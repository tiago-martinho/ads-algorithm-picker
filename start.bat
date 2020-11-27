@ECHO OFF

ECHO.
ECHO Building Docker images...
ECHO -------------------------
ECHO.

CALL docker-compose build


ECHO.
ECHO Starting services...
ECHO -------------------------
ECHO.

CALL docker-compose -p ads up

PAUSE