# DebugGraphics2D
The following library is a superset of Java's Graphics2D class which allows users to display markers at primitives' control points for debugging purposes.

## Example
```Java
DebugGraphics2D g2d = new DebugGraphics2D((Graphics2D) g);
g2d.fillRect(20, 20, 300, 100); // markers at the rect corners
g2d.translate(50, 50); // works with transforms
g2d.fill(leafPath); // works with GeneralPath
g2d.startDeferringMarkers(); // start drawing markers on the top layer
g2d.fillOval(300, 300, 100, 100);
g2d.setDebugColor(Color.yellow); // change marker color
g2d.setDebugMarkerSize(4) // change marker size, works better if divisible by 2
g2d.disableDebugging(); // disable marker drawing
g2d.fillRect(100, 100, 100, 100); // no markers after disableDebugging()
g2d.enableDebugging(); // enable marker drawing
g2d.fillRect(100, 100, 100, 100); // markers will be shown after enableDebugging()
g2d.drawDeferredMarkers(); // draw all of the deferred markers on the top layer
```

## Compiling
To compile this library:
1. Run `javac -d out/cls src/ru/vsu/cs/cg/sdm/debug_graphics2d/*.java` in the project directory to compile all of the classes to .class files
2. Run `jar cf DebugGraphics2D.jar -C out/cls .` to create a .jar file
--------
Alternetively, if you are using Intellij Idea: 
1. Go to File > Project Structure > Artifacts and click on the plus icon. Select JAR > From modules with dependencies
2. Go to Build > Build Artifacts. Your .jar file is now in the out/artifacts directory