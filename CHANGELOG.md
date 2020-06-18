# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [UNRELEASED]

## [v1.0.0] - 18/06/2020 
### Changed
- Move release logic to fastlane

## [1.0-RC05] - 26/05/2020
### Changed
- Upgrade Android Gradle Plugin to 3.6.3
- Upgrade Kotlin to 1.3.72
### Added
- Add units tests in the sample app
- Run Danger to validate PR
- Add a contribution guide
- Add `showState(@IdRes id: Int, showTransitions: Boolean)` to StatefulLayout to disable transition
- Stateful layouts now have a `areTransitionsEnabled` attribute to enable/disable transitions
- Optional error message argument for `showError()` method.
### Fixed
- Remove duplicates in view hierarchy when trying to overload an existing state
- State constructor parameters were reversed, breaking up Android Studio preview

## [1.0-RC4] - 27/04/2020
### Changed
- Hide all states by default
### Breaking change
- Fix typo on xml attribute `defaultExitrTransition` that has been renamed to `defaultExitTransition`
### Fixed
- Fix enter/exit defaultTransition sharing the same animation instance for all the states. Switching
quickly between states was resulting in animation issue.
- Fix crash when restoring a StatefulLayout that has no state

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
