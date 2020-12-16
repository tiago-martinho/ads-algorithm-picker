package pt.ads.server.algorithm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.reflections8.Reflections;
import org.reflections8.scanners.SubTypesScanner;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.abyss.ABYSSBuilder;
import org.uma.jmetal.algorithm.multiobjective.cdg.CDGBuilder;
import org.uma.jmetal.algorithm.multiobjective.cellde.CellDE45;
import org.uma.jmetal.algorithm.multiobjective.dmopso.DMOPSOBuilder;
import org.uma.jmetal.algorithm.multiobjective.espea.ESPEABuilder;
import org.uma.jmetal.algorithm.multiobjective.espea.util.EnergyArchive.ReplacementStrategy;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3Builder;
import org.uma.jmetal.algorithm.multiobjective.gwasfga.GWASFGA;
import org.uma.jmetal.algorithm.multiobjective.ibea.IBEABuilder;
import org.uma.jmetal.algorithm.multiobjective.mocell.MOCellBuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.paes.PAESBuilder;
import org.uma.jmetal.algorithm.multiobjective.pesa2.PESA2Builder;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.algorithm.multiobjective.rnsgaii.RNSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOABuilder;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.algorithm.multiobjective.wasfga.WASFGA;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmBuilder;
import org.uma.jmetal.util.archive.impl.CrowdingDistanceArchive;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.neighborhood.impl.C9;
import pt.ads.server.model.AlgorithmOptions;

/**
 * Uses a factory pattern to abstract away the instantiation of the algorithms.
 */
@Slf4j
public class AlgorithmFactory {

	private static final Reflections reflections = initializeReflections();
	private static final String BASE_PACKAGE = "org.uma.jmetal.algorithm";
	private static final long DEFAULT_NUMERICAL_VALUE = 5;
	private static final boolean USE_FALLBACK_METHOD = true;


	/**
	 * Instantiates a algorithm through reflection based on a name.
	 *
	 * @param algorithmName the name of the algorithm
	 * @param options the options for the algorithm
	 * @param problem the problem type
	 * @param <T> type solution type (double, integer, etc)
	 * @return the algorithm, or {@code null} is none found
	 */
	@Nullable
	public static <T extends Solution<?>> Algorithm<List<T>> getAlgorithm(@NonNull String algorithmName, @NonNull AlgorithmOptions options, @NonNull Problem<T> problem) {
		Algorithm<List<T>> algorithm = getAlgorithmReflection(algorithmName, options, problem);

		if (USE_FALLBACK_METHOD && algorithm == null) {
			log.trace("Unable to initialize algorithm {} through reflection. Using fallback method", algorithmName);
			algorithm = getAlgorithmFallback(algorithmName, options, problem);
		}

		if (algorithm == null) {
			log.warn("Unable to initialize algorithm " + algorithmName);
		}

		return algorithm;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public static <T extends Solution<?>> Algorithm<List<T>> getAlgorithmReflection(@NonNull String algorithmName, @NonNull AlgorithmOptions options, @NonNull Problem<T> problem) {
		log.info("Initializing algorithm '" + algorithmName + "' through reflection");

		try {
			final Map<Class, Object> mapper = createMapper(problem, options);

			List<Class<?>> classes = findClassesByName(algorithmName);
			for (Class<?> clazz : classes) {
				log.trace("Trying class: " + clazz);

				// Find constructors - ordered by the one with fewer parameters
				List<Constructor<?>> constructors = Arrays.stream(clazz.getDeclaredConstructors())
						.sorted(Comparator.comparingInt(Constructor::getParameterCount))
						.collect(Collectors.toList());

				for (Constructor<?> constructor : constructors) {
					log.trace("Trying constructor: " + constructor);

					try {
						Class<?>[] paramTypes = constructor.getParameterTypes();
						Object[] params = new Object[paramTypes.length];

						for (int i = 0; i < params.length; i++) {
							Class<?> type = paramTypes[i];
							Object value = mapper.get(type);
							params[i] = value;

							if (value == null) {
								log.trace("No value for type: {}", type);
							}
						}

						Object o = constructor.newInstance(params);

						// Fill the fields have no defaults (i.e. populationSize, crossoverOperator, ...)
						for (Field field : clazz.getDeclaredFields()) {
							Object defaultValue = getFieldDefaultValue(field.getName(), options, mapper);
							if (defaultValue != null) {
								setFieldDefaultValue(clazz, o, field.getName(), defaultValue);
							}
						}

						if (o instanceof AlgorithmBuilder) {
							AlgorithmBuilder<?> builder = (AlgorithmBuilder<?>) o;
							return (Algorithm<List<T>>) builder.build();
						} else if (o instanceof Algorithm) {
							return (Algorithm<List<T>>) o;
						}
					} catch (Exception ignored) { }
				}
			}
		} catch (Exception e) {
			log.warn("Unable to instantiate algorithm though reflection: {}", algorithmName, e);
		}

		return null;
	}

	@Nullable
	private static Object getFieldDefaultValue(String name, AlgorithmOptions options, Map<Class, Object> mapper) {
		switch (name) {
		case "populationSize":
			return options.populationSize;
		case "maxIterations":
			return options.iterations;
		case "maxEvaluations":
			return options.iterations * options.populationSize;
		case "crossoverOperator":
			return mapper.get(CrossoverOperator.class);
		case "mutationOperator":
			return mapper.get(MutationOperator.class);
		case "selectionOperator":
			return mapper.get(SelectionOperator.class);
		default:
			return null;
		}
	}

	private static <T extends Solution<?>> Map<Class, Object> createMapper(Problem<T> problem, AlgorithmOptions options) {
		final Map<Class, Object> mapper = new HashMap<>();

		mapper.put(short.class,   Long.valueOf(DEFAULT_NUMERICAL_VALUE).shortValue());
		mapper.put(Short.class,   Long.valueOf(DEFAULT_NUMERICAL_VALUE).shortValue());
		mapper.put(int.class,     Long.valueOf(DEFAULT_NUMERICAL_VALUE).intValue());
		mapper.put(Integer.class, Long.valueOf(DEFAULT_NUMERICAL_VALUE).intValue());
		mapper.put(long.class,    DEFAULT_NUMERICAL_VALUE);
		mapper.put(Long.class,    DEFAULT_NUMERICAL_VALUE);
		mapper.put(float.class,   Long.valueOf(DEFAULT_NUMERICAL_VALUE).floatValue());
		mapper.put(Float.class,   Long.valueOf(DEFAULT_NUMERICAL_VALUE).floatValue());
		mapper.put(double.class,  Long.valueOf(DEFAULT_NUMERICAL_VALUE).doubleValue());
		mapper.put(Double.class,  Long.valueOf(DEFAULT_NUMERICAL_VALUE).doubleValue());
		mapper.put(Problem.class, problem);
		mapper.put(CrossoverOperator.class,     getCrossoverOperator(problem, options));
		mapper.put(MutationOperator.class,      getMutationOperator(problem, options));
		mapper.put(SelectionOperator.class,     getSelectionOperator(problem, options));
		mapper.put(SolutionListEvaluator.class, new SequentialSolutionListEvaluator<>());

		return mapper;
	}

	private static void setFieldDefaultValue(Class<?> clazz, Object instance, String fieldName, Object defaultValue) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);

			Object value = field.get(instance);

			boolean isEmpty = value == null;
			boolean isDefault = (value instanceof Number && ((Number) value).longValue() == DEFAULT_NUMERICAL_VALUE);
			if (isEmpty || isDefault) {
				field.set(instance, defaultValue);
			}
		} catch(Exception ignored) { }
	}

	public static List<Class<?>> findClassesByName(String name) {
		return Stream.concat(findAlgorithmBuilderClasses(name), findAlgorithmClasses(name))
				.collect(Collectors.toList());
	}

	@SuppressWarnings("rawtypes")
	private static Stream<Class<? extends Algorithm>> findAlgorithmClasses(String name) {
		return findSubClassesByName(name, Algorithm.class);
	}

	@SuppressWarnings("rawtypes")
	private static Stream<Class<? extends AlgorithmBuilder>> findAlgorithmBuilderClasses(String name) {
		return findSubClassesByName(name, AlgorithmBuilder.class);
	}

	private static <T> Stream<Class<? extends T>> findSubClassesByName(String name, Class<T> clazz) {
		return reflections.getSubTypesOf(clazz).stream()
				.filter(c -> c.getSimpleName().toUpperCase().startsWith(name))
				.sorted(Comparator.comparingInt(a -> a.getSimpleName().length())); // sort by length - smallest first
	}

	@SuppressWarnings("SameParameterValue")
	private static Reflections initializeReflections() {
		return new Reflections(BASE_PACKAGE, new SubTypesScanner(true));
	}


	@Nullable
	@SuppressWarnings({ "SpellCheckingInspection", "unchecked" })
	public static <T extends Solution<?>> Algorithm<List<T>> getAlgorithmFallback(@NonNull String algorithmName, @NonNull AlgorithmOptions options, @NonNull Problem<T> problem) {
		log.info("Initializing algorithm '" + algorithmName + "' through fallback method");

		try {
			List<Double> referencePoint;

			switch (algorithmName) {
			case "ABYSS":
				if (problem instanceof DoubleProblem) {
					return (Algorithm<List<T>>) (Algorithm<?>) new ABYSSBuilder((DoubleProblem) problem, new CrowdingDistanceArchive<>(10))
							.setPopulationSize(options.populationSize)
							.setMaxEvaluations(options.iterations)
							.setCrossoverOperator((CrossoverOperator<DoubleSolution>) getCrossoverOperator(problem, options))
							.setMutationOperator((MutationOperator<DoubleSolution>) getMutationOperator(problem, options))
							.build();
				} else {
					log.debug("The ABYSS algorithm is only applicable to problems of type \"double\"");
					return null;
				}

			case "CDG":
				if (problem instanceof DoubleProblem) {
					return (Algorithm<List<T>>) (Algorithm<?>) new CDGBuilder((DoubleProblem) problem)
							.setPopulationSize(options.populationSize)
							.setMaxEvaluations(options.iterations)
							.setCrossover((CrossoverOperator<DoubleSolution>) getCrossoverOperator(problem, options))
							.build();
				} else {
					log.debug("The CDG algorithm is only applicable to problems of type \"double\"");
					return null;
				}

			case "CELLDE":
				if (problem instanceof DoubleProblem) {
					return (Algorithm<List<T>>) (Algorithm<?>) new CellDE45(
							(Problem<DoubleSolution>) problem,
							options.iterations,
							options.populationSize,
							new CrowdingDistanceArchive<>(100),
							new C9<>((int) Math.sqrt(100), (int) Math.sqrt(100)),
							new BinaryTournamentSelection<>(new RankingAndCrowdingDistanceComparator<>()),
							new DifferentialEvolutionCrossover(),
							20,
							new SequentialSolutionListEvaluator<>());
				} else {
					log.debug("The CellDE algorithm is only applicable to problems of type \"double\"");
					return null;
				}

			case "DMOPSO":
				if (problem instanceof DoubleProblem) {
					return (Algorithm<List<T>>) (Algorithm<?>) new DMOPSOBuilder((DoubleProblem) problem)
							.setSwarmSize(options.populationSize)
							.setMaxIterations(options.iterations)
							.build();
				} else {
					log.debug("The DMOPSO algorithm is only applicable to problems of type \"double\"");
					return null;
				}

			case "ESPEA":
				ESPEABuilder<T> builder = new ESPEABuilder<>(problem, getCrossoverOperator(problem, options), getMutationOperator(problem, options));
				builder.setPopulationSize(options.populationSize);
				builder.setMaxEvaluations(options.iterations);
				builder.setReplacementStrategy(ReplacementStrategy.WORST_IN_ARCHIVE);
				return builder.build();

			case "GDE3":
				if (problem instanceof DoubleProblem) {
					return (Algorithm<List<T>>) (Algorithm<?>) new GDE3Builder((DoubleProblem) problem)
							.setPopulationSize(options.populationSize)
							.setMaxEvaluations(options.iterations)
							.setCrossover(new DifferentialEvolutionCrossover())
							.setSelection(new DifferentialEvolutionSelection())
							.setSolutionSetEvaluator(new SequentialSolutionListEvaluator<>())
							.build();
				} else {
					log.debug("The GDE3 algorithm is only applicable to problems of type \"double\"");
					return null;
				}

			case "GWASFGA":
				if (problem instanceof DoubleProblem) {
					return (Algorithm<List<T>>) (Algorithm<?>) new GWASFGA<>(
							(DoubleProblem) problem,
							options.populationSize,
							options.iterations,
							(CrossoverOperator<DoubleSolution>) getCrossoverOperator(problem, options),
							(MutationOperator<DoubleSolution>) getMutationOperator(problem, options),
							new BinaryTournamentSelection<>(new RankingAndCrowdingDistanceComparator<>()),
							new SequentialSolutionListEvaluator<>(),
							0.01);
				} else {
					log.debug("The GWASFGA algorithm is only applicable to problems of type \"double\"");
					return null;
				}

			case "IBEA":
				if (problem instanceof DoubleProblem) {
					return (Algorithm<List<T>>) (Algorithm<?>) new IBEABuilder((DoubleProblem) problem)
							.setPopulationSize(options.populationSize)
							.setMaxEvaluations(options.iterations)
							.setArchiveSize(100)
							.setCrossover((CrossoverOperator<DoubleSolution>) getCrossoverOperator(problem, options))
							.setMutation((MutationOperator<DoubleSolution>) getMutationOperator(problem, options))
							.setSelection(new BinaryTournamentSelection<>())
							.build();
				} else {
					log.debug("The IBEA algorithm is only applicable to problems of type \"double\"");
					return null;
				}

			case "MOCELL":
				return new MOCellBuilder<>(problem, getCrossoverOperator(problem, options), getMutationOperator(problem, options))
						.setPopulationSize(options.populationSize)
						.setMaxEvaluations(options.iterations)
						.setSelectionOperator(getSelectionOperator(problem, options))
						.setArchive(new CrowdingDistanceArchive<>(100))
						.build();

//		case "MOCHC":
//			return new MOCHCBuilder(problem).build(); // TODO BINARY

//		case "MOEAD":
//			return new MOEADBuilder(problem, new MOEADBuilder.Variant()).build(); // TODO DOUBLE

//		case "MOMBI":
//			return new MOMBI<>(problem, options.iterations, getCrossoverOperator(problem, options), getMutationOperator(problem, options), getSelectionOperator(problem, options), ...);

			case "NSGAII":
				return new NSGAIIBuilder<>(problem, getCrossoverOperator(problem, options), getMutationOperator(problem, options), options.populationSize)
						.setMaxEvaluations(options.iterations)
						.setSelectionOperator(getSelectionOperator(problem, options))
						.build();

			case "NSGAIII":
				return new NSGAIIIBuilder<>(problem)
						.setPopulationSize(options.populationSize)
						.setMaxIterations(options.iterations)
						.setCrossoverOperator(getCrossoverOperator(problem, options))
						.setMutationOperator(getMutationOperator(problem, options))
						.setSelectionOperator(getSelectionOperator(problem, options))
						.build();

//		case "OMOPSO":
//			return new OMOPSOBuilder(problem, ...); // TODO DOUBLE

			case "PAES":
				return new PAESBuilder<>(problem)
						.setMutationOperator(getMutationOperator(problem, options))
						.setArchiveSize(100)
						.build();

			case "PESA2":
				return new PESA2Builder<>(problem, getCrossoverOperator(problem, options), getMutationOperator(problem, options))
						.setPopulationSize(options.populationSize)
						.setMaxEvaluations(options.iterations)
						.build();

			case "RANDOMSEARCH":
				return new RandomSearchBuilder<>(problem)
						.setMaxEvaluations(options.iterations)
						.build();

			case "RNSGAII":
				referencePoint = new ArrayList<>();
				referencePoint.add(0.1); referencePoint.add(0.6);
				referencePoint.add(0.3); referencePoint.add(0.6);
				referencePoint.add(0.5); referencePoint.add(0.2);
				referencePoint.add(0.7); referencePoint.add(0.2);
				referencePoint.add(0.9); referencePoint.add(0.0);

				return new RNSGAIIBuilder<>(problem, getCrossoverOperator(problem, options), getMutationOperator(problem, options), referencePoint, 0.0045)
						.setPopulationSize(options.populationSize)
						.setMaxEvaluations(options.iterations)
						.build();

//		case "SMPSO":
//			return new SMPSOBuilder(problem, ...) // TODO DOUBLE

			case "SMSEMOA":
				return new SMSEMOABuilder<>(problem, getCrossoverOperator(problem, options), getMutationOperator(problem, options))
						.setPopulationSize(options.populationSize)
						.setMaxEvaluations(options.iterations)
						.setSelectionOperator(getSelectionOperator(problem, options))
						.build();

			case "SPEA2":
				return new SPEA2Builder<>(problem, getCrossoverOperator(problem, options), getMutationOperator(problem, options))
						.setPopulationSize(options.populationSize)
						.setMaxIterations(options.iterations)
						.setSelectionOperator(getSelectionOperator(problem, options))
						.setK(1)
						.build();

			case "WASFGA":
				referencePoint = new ArrayList<>();
				referencePoint.add(0.0);
				referencePoint.add(0.0);

				return new WASFGA<>(problem, options.populationSize, options.iterations,
						getCrossoverOperator(problem, options),
						getMutationOperator(problem, options),
						getSelectionOperator(problem, options),
						new SequentialSolutionListEvaluator<>(),
						0.01,
						referencePoint);
			default:
				log.warn("Did not find algorithm named '" + algorithmName + "'");
				return null;
			}
		} catch (Exception e) {
			log.warn("Unable to instantiate algorithm though fallback: {}", algorithmName, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private static @Nullable <T> CrossoverOperator<T> getCrossoverOperator(Problem<T> problem, AlgorithmOptions options) {
		if (problem instanceof AbstractDoubleProblem) {
			return (CrossoverOperator<T>) new SBXCrossover(options.crossoverProbability, options.crossoverDistributionIndex);
		} else if (problem instanceof AbstractIntegerProblem) {
			return (CrossoverOperator<T>) new IntegerSBXCrossover(options.crossoverProbability, options.crossoverDistributionIndex);
		} else if (problem instanceof AbstractBinaryProblem) {
			return (CrossoverOperator<T>) new SinglePointCrossover(options.crossoverProbability);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private static @Nullable <T> MutationOperator<T> getMutationOperator(Problem<T> problem, AlgorithmOptions options) {
		double probability = options.mutationProbability / problem.getNumberOfVariables();

		if (problem instanceof AbstractDoubleProblem) {
			return (MutationOperator<T>) new PolynomialMutation(probability, options.mutationDistributionIndex);
		} else if (problem instanceof AbstractIntegerProblem) {
			return (MutationOperator<T>) new IntegerPolynomialMutation(probability, options.mutationDistributionIndex);
		} else if (problem instanceof AbstractBinaryProblem) {
			return (MutationOperator<T>) new BitFlipMutation(probability);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unused")
	private static @NonNull <T extends Solution<?>> BinaryTournamentSelection<T> getSelectionOperator(Problem<T> problem, AlgorithmOptions options) {
		return new BinaryTournamentSelection<>(new RankingAndCrowdingDistanceComparator<>());
	}

}
