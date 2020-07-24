# Stateful Layout
[![Release](https://jitpack.io/v/fabernovel/stateful-layout.svg)](https://jitpack.io/#fabernovel/stateful-layout)

Android StatefulLayout

[Changelog](CHANGELOG.md)

## Contribution
To contribute please read [the Contribution Guide](docs/CONTRIBUTING.md)

## Installation
Add Jitpack:
```
repositories {
  ...
  maven { url 'https://jitpack.io' }
}
```

Add the dependency:
```
dependencies {
  implementation 'com.github.fabernovel:stateful-layout:<Version>'
}
```

## Usage
StatefulLayout allows to create different state for a screen (loading state, content state, missing permission state, etc).
States are referenced using an android resource id.

### StatefulLayout

`StatefulLayout` is a view group containing a set of `State`.
Each `State` must have an id which will be used to display them when needed.

To use it, add a `StatefulLayout` to a layout and put a `State` with `android:id="@id/stateContent` to define the normal content state.
``` XML
 <com.fabernovel.statefullayout.StatefulLayout
        android:id="@+id/statefulLayout"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:defaultEnterTransition="@anim/fragment_fade_enter"
        app:defaultExitTransition="@anim/fragment_fade_exit"
        app:initialState="@id/stateLoading"
        app:layout_constraintBottom_toTopOf="@id/actions"
        app:layout_constraintTop_toTopOf="parent"
        >
        <com.fabernovel.statefullayout.State
            android:id="@id/stateContent"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            <!-- your state content -->
        </com.fabernovel.statefullayout.State>
    </com.fabernovel.statefullayout.StatefulLayout>
```

#### Default states:
By default, three states are provided:
1. Loading state: `stateLoading`:
  A progress bar in the middle of the screen.
2. Content state: `stateContent`
  The normal content screen
3. Error state: `stateError`
  An error screen with a retry button

To overwrite the view displayed by a default state, there are two ways:

1. Add a `State` with the id you want to overwrite inside your `StatefulLayout`.
For example to create a custom error state:
```XML
 <com.fabernovel.statefullayout.StatefulLayout
        android:id="@+id/statefulLayout"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:defaultEnterTransition="@anim/fragment_fade_enter"
        app:defaultExitTransition="@anim/fragment_fade_exit"
        app:initialState="@id/stateLoading"
        app:layout_constraintBottom_toTopOf="@id/actions"
        app:layout_constraintTop_toTopOf="parent"
        >
        <com.fabernovel.statefullayout.State
            android:id="@id/stateError"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            <!-- your error state content -->
        </com.fabernovel.statefullayout.State>
    </com.fabernovel.statefullayout.StatefulLayout>
```
2. Pass a layout resource to a `StatefulLayout` layout view:
  - `app:loadingStateLayout` for the loading state.
  - `app:errorStateLayout` for the error state.

#### Custom state:
To add a custom state, add a `State` inside a `StatefulLayout`.
*Warnings*:
- State can only have *one* child view
- State *must* have an id.

State's child can be set:
1. In the layout by set a layout as `contentLayout` attribute.
```xml
<com.fabernovel.statefullayout.State
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:id="@+id/stateCustom"
            app:contentLayout="@layout/state_custom"
            />
```
2. In the layout, by adding a view as a child of the state
```xml
<com.fabernovel.statefullayout.State
            android:id="@id/stateContent"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:enterTransition="@anim/fragment_open_enter"
            app:exitTransition="@anim/fragment_close_exit"
            >

            <androidx.constraintlayout.widget.ConstraintLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="@color/colorAccent"
                >
                <!--CONTENT-->
            </androidx.constraintlayout.widget.ConstraintLayout>
        </com.fabernovel.statefullayout.State>
```

`State` can also be added programmatically to a `StatefulLayout` (they still need to have an id).

You can access a state content view using `contentView` or `requireContentView` which check
if view is null.

#### Change the displayed state:
To change the displayed state, call `showState(@IdRes id: Int)` with your state's id.
Default state also have their kotlin extensions for convenience:
- `showError(onRetryListener: ((View) -> Unit = {})?)`
- `showLoading()`
- `showContent()`

#### Add listeners to state's views
If the state is in a layout, it can be accessed like any other view.
To access views from layout that are inflated by the `StatefulLayout`, the recommended way is to
use Android View Binding. (https://developer.android.com/topic/libraries/view-binding)
To get a stateView you can use `requireStateView(<stateId>)` extension.

``` kotlin
// ...
val errorStateView = binding.requireStateView(R.id.stateError)       
val errorBinding = StateErrorBinding.bind(errorStateView)

errorBinding.stateErrorRetryButton.setOnClickListener {
    // do whatever
}
```     

#### Theme:
 The library adds a theme attribute `statefulLayoutStyle` which take a `StatefulLayout` style.
 Extend `Widget.Stateful.StatefulLayout` and change the following attributes:  

`StatefulLayout`:
| Attribute  | Definition |
| ------------- | ------------- |
| loadingStateLayout  | A layout reference used to inflate the loading state view. (optional)  |
| errorStateLayout  | A layout reference used to inflate the error state view. (optional)  |
| initialState  | Id of the initially displayed state. (by default: none)  |
| defaultEnterTransition  | Default enter transition. (by default: none)  |
| defaultExitTransition  | Default exit transition. (by default: none)  |
| areTransitionsEnabled | Play transitions when changing state (by default: true) |
`State` also have an theme attribute `stateStyle` and a default style `Widget.Stateful.State`
| Attribute  | Definition |
| ------------- | ------------- |
| enterTransition  | Enter transition. (by default: none)  |
| exitTransition  | Exit transition. (by default: none)  |

#### Animation

By default, no animation are played on state transition. To add your own enter and exit
animation:
- On a `StatefulLayout` using `defaultEnterTransition` and `defaultExitTransition`. Those transitions
will be played on every state change excluding state with their own transitions.
- On a `State` using `enterTransition` and `exitTransition` which override the parent `StatefulLayout`
 transitions.

Animation can be either an animator resource or an animation resource.
To load a state transition programmatically, use the helper class `StateTransitions`.
`StateTransitions` allow to create a `StateTransition` from:
- An `Animator` (https://developer.android.com/reference/android/animation/Animator)
- An `Animation` (https://developer.android.com/reference/android/view/animation/Animation)
- A resource version of an animator or an animation.
- A callback (see [MainFragment](https://github.com/faberNovel/stateful-layout/blob/develop/sample-app/src/main/java/com/fabernovel/statefullayout/sample/ui/main/MainFragment.kt))  

#### State-change regulation

You can set a minimal waiting time by state (i.e. to avoid blinking if you change state very
frequently) by calling `setMinimalTimeToWaitBetweenStateChanges(time)` and then using
`statefulLayout.requestStateChange(stateId)` instead of `showState(stateId)`.

While `showState(stateId)` is immediate and guaranteed, `requestStateChange(stateId)` leading to
a state change is more hypothetical and will depend on other requests sent slightly before or after.

Basically:

- If requests are chained (almost zero milliseconds in between) then all will be ignored except for
 the last one.
- If one or many requests are sent during the "MinimalTimeToWaitBetweenStateChanges" after a request
 has been handled then only the most recent one will be forwarded to the handler (right after that
 minimal time has elapsed).

More visual example with a MinimalTimeToWaitBetweenStateChanges = 500 ms

| Time (ms)     | 0 | 5 | 100 | 200 | 500 | 550 | 550 | 550 | 555 | 2_000 | 2_005 | 3_000 | 3_005 |
|---------------|---|---|-----|-----|-----|-----|-----|-----|-----|-------|-------|-------|-------|
| Request sent  | A | - | B   | C   | -   | D   | E   | F   | -   | G     | -     | H     | -     |
| State changed | - | A | -   | -   | C   | -   | -   | -   | F   | -     | G     | -     | H     |

Please note that due to platform limitations the durations will not match the exact number of
milliseconds theoretically expected.

## License

ADUtils is released under the Apache 2.0 license. [See LICENSE](LICENSE) for details.
