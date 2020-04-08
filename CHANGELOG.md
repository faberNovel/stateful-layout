# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).
## [Unreleased]
### Added
- State content view can be set using a layout resource.
- The new contentLayout State's attribute allows to specify a layout to inflate.  
### Changed
- State now throws an IllegalArgumentException if multiples views are added using addView.

## [1.0-RC02]
### Fixed
- Fix NPE when showing an error state that doesn't contain a retry button in its layout
### Changed
- Update builds tool to 29.0.3

## [1.0-RC01] - 07/04/2020
- First release
