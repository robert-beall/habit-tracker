# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Unreleased

### Added
- User Entity, DTO, Repository, Service, and Controller
- Role Entity, DTO, and repository
- Privilege Entity, DTO, and repository
- ModelMapperConfig.java
- ModelMapper.md documentation
- WebSecurityConfig.java
- Makefile with startup and teardown commands
- AuthController for authentication specific endpoints
- UserDetailsServiceImpl class
- UnauthorizedEntrypoint for failed login

### Changed
- Backend env variables
- Parameterized application.properties
- Basic spring application test assertion
- Moved env file to resources folder
- Added free command to Makefile

### Fixed
- Skip tests on backend build

## [0.0.1] - 2024-07-18

### Added
- docs folder

### Changed
- work-cited.md moved to docs

### Removed
- Unused src/database/docker-compose.yml

## [0.0.0] - 2024-07-16

### Added
- Boilerplate spring code
- Docker compose for backend and database
- Basic documentation