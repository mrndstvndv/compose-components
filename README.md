# Compose Components

An Android Compose library extracted from the settings UI in Search, with a small app that previews the available components.

## Components

The components Android library provides:

- `SettingsDivider` for a themed horizontal divider.
- `SettingsGroup` for a rounded, tonal container around related settings.
- `SettingsHeader` for a screen title with optional subtitle and back action.
- `SettingsNavigationRow` for tappable settings destinations with optional icon, badge, and chevron.
- `SettingsSection` for a labeled group of content with optional subtitle.
- `SettingsSingleChoiceSegmentedButtons` for a generic single-choice segmented control.
- `SettingsSliderRow` for a titled slider with optional subtitle and required `valueText`.
- `SettingsSwitch` for a labeled switch with optional icon and subtitle.

Components render with Material 3, read typography, shapes, and colors from the host app's `MaterialTheme`, and let the host control state through parameters and callbacks.

## Build and install the showcase

    ./gradlew :showcase:assembleDebug
    adb install -r showcase/build/outputs/apk/debug/showcase-debug.apk

Build the reusable library with:

    ./gradlew :components:assembleRelease

The showcase app source is in `showcase/src/main/java/com/mrndstvndv/composecomponents/showcase/MainActivity.kt`. Include the components module in a Gradle project and add `implementation(project(":components"))` to consume it locally.

## GitHub Packages

Stable releases are created by pushes to `main`. The first push to the repository should use that branch:

    git push -u origin main

The workflow builds the showcase APK and release AAR, then semantic-release creates a stable `vX.Y.Z` GitHub Release and publishes the AAR to GitHub Packages. Commit messages follow Angular Conventional Commits. `feat` creates a minor release. `fix`, `update`, `ui`, and `perf` create patch releases. A `BREAKING CHANGE:` footer creates a major release. `docs`, `ci`, and `chore` commits do not release. The npm project is private and only installs release tooling.

Add the GitHub Packages Maven repository to a consuming project's Gradle settings or build file:

    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/mrndstvndv/compose-components")
            credentials {
                username = providers.gradleProperty("gpr.user").orNull ?: System.getenv("GITHUB_ACTOR")
                password = providers.gradleProperty("gpr.key").orNull ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }

Then depend on a published version:

    dependencies {
        implementation("com.mrndstvndv:compose-components:1.2.3")
    }

For local development, set `gpr.user` and `gpr.key` in `~/.gradle/gradle.properties`, or supply `GITHUB_ACTOR` and `GITHUB_TOKEN` as environment variables. The token needs read access to the package.

## License and source

The component implementations are copied from `Search/app/src/main/java/com/mrndstvndv/search/ui/components/settings/` and retain Search's GPL-3.0 license. See `LICENSE`.
