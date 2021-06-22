# FractalMatic
This app is a simple 2d fractal generator that uses JavaFx framework.
Fractalmatic has only one type of fractal at the moment, but the plan is to add more types in the future.

## Circles Fractal

The idea is super simple; the fractal starts with drawing the main/parent circle, then its evenly separated children - circles smaller than the parent circle- are drawn around the main/parent circle, and this process repeats for every child circle -the child becomes main it gets its children. Even though this process is very simple, the results are amazing. Here are some screenshots,


<img src="screenshots/ex2.jpg" width="350"> <img src="screenshots/ex3.jpg" width="350">
<img src="screenshots/ex4.jpg" width="350"> <img src="screenshots/ex5.jpg" width="350">
<img src="screenshots/ex6.jpg" width="350"> <img src="screenshots/ex7.jpg" width="350">

#### Configurations

As you see, each fractal is completely different from others, it's because you can change many parameters that will change the final result. You can see what parameters are available in the configuration menu screenshot below,

<img src="screenshots/ex_config_menu.jpg" width="350"> 

#### Animation

You can also animate some of these parameters, to be exact, everything below and including the start angle in the configuration menu.  Animation is basically an oscillation, and you can define amplitude and speed values.

<img src="screenshots/ex_anim_menu.jpg" width="350"> 

#### Randomizer

Randomizer is a fun tool. You choose what to randomize and let the randomizer create a configuration for you, including animation. The keyboard shortcut is **CTRL + R** Randomizer will generate configuration within the specified circles limit.

<img src="screenshots/ex_randomizer_menu.jpg" width="700">

