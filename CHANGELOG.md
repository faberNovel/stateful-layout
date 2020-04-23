# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0-RC03]
### Added
- State content view can be set using a layout resource.
- The new contentLayout State's attribute allows to specify a layout to inflate.
- Documentation on public classes.  
### Breaking change
- Default initial state is no longer `stateContent`, by default no state is displayed.
### Changed
- State now throws an IllegalArgumentException if multiples views are added using addView.
- Mark DefaultTransitionListenerHandler as internal
- All method in StateTransitionListener interface now have empty default implementation.
### Removed
- DefaultStateTransitionListener was removed in favor of Kotlin interface default implementation in
StateTransitionListener
### Fixed
- Remove launcher icons, they were leaking to project, replacing ones project launcher icons under certain circumstance.

## [1.0-RC02]
### Fixed
- Fix NPE when showing an error state that doesn't contain a retry button in its layout
### Changed
- Update builds tool to 29.0.3

## [1.0-RC01] - 07/04/2020
- First release
