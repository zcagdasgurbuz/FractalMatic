# FractalMatic
This app is a simple 2d fractal generator that uses JavaFx framework.
Fractalmatic has only one type of fractal at the moment, but the plan is to add more types in the future.

<a href="https://github.com/zcagdasgurbuz/FractalMatic/releases" style="font-size: 20px;">Download stand-alone package </a>


---

## Circles Fractal

The idea is super simple; the fractal starts with drawing the main/parent circle, then its evenly separated children - circles smaller than the parent circle- are drawn around the main/parent circle, and this process repeats for every child circle -the child becomes main it gets its children. Even though this process is very simple, the results are amazing. Here are some screenshots,


<img src="screenshots/ex2.jpg" width="350"> <img src="screenshots/ex3.jpg" width="350">
<img src="screenshots/ex4.jpg" width="350"> <img src="screenshots/ex5.jpg" width="350">
<img src="screenshots/ex6.jpg" width="350"> <img src="screenshots/ex7.jpg" width="350">

---

#### Configurations

As you see, each fractal is completely different from others, it's because you can change many parameters that will change the final result. You can see what parameters are available in the configuration menu screenshot below,


<img src="screenshots/ex_config_menu.jpg" width="350">

---

#### Animation

You can also animate some of these parameters, to be exact, everything below and including the start angle in the configuration menu.  Animation is basically an oscillation, and you can define amplitude and speed values. Animation performance depends on the system performance, and frame rate might drop significantly with large numbers of circles. You might want to limit the maximum number of circles for the animation to prevent FractalMatic from freezing.

<img src="screenshots/ex_anim_menu.jpg" width="350">  
<img src="screenshots/animation_ex.gif" width="700">  
<img src="screenshots/animation_ex1.gif" width="700">  

---

#### Randomizer

Randomizer is a fun tool. You choose what to randomize and let the randomizer create a configuration for you, including animation. The keyboard shortcut is **CTRL + R**. Note that the randomizer generates a configuration within the specified number of circles range. If the number of circles is too high, it might take a second fractal to show up. You might limit the maximum limits based on your system performance in randomizer menu.

<img src="screenshots/ex_randomizer_menu.jpg" width="700">

---

#### Save / Load

After creating interesting fractals by Randomizer or setting parameters manually, you can save the configurations you like and load them later.

<img src="screenshots/ex_save_menu.jpg" width="350">

---

#### Drag / Zoom / Full Screen / Capture Mode

You also use your mouse to drag the fractal around and zoom in. **Double-click** will reset the position and the zoom. Press **F11** for fullscreen mode.  
For capture mode use **CTRL + SHIFT + C** key combinations. It toggles the visibility of the menu and home buttons.

---

## For more, Watch Demo On Youtube

[![Watch the video](https://img.youtube.com/vi/Tju0XF1vj8o/maxresdefault.jpg)](https://youtu.be/Tju0XF1vj8o?vq=hd1080)

---

### Java Version

Java version : 11  
JavaFx version : 15  
Built by using Maven
