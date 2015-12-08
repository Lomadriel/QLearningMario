/*******************************************************************************
 * Copyright (C) 2015 BOULMIER Jérôme, CORTIER Benoît
 *
 * This software is provided 'as-is', without any express or implied
 * warranty.  In no event will the authors be held liable for any damages
 * arising from the use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 * 1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 *******************************************************************************/

package fr.utbm.tc.qlearningmario.mario;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.arakhne.afc.vmutil.Resources;
import org.arakhne.afc.vmutil.locale.Locale;

import fr.utbm.tc.qlearningmario.mario.entity.Entity;
import fr.utbm.tc.qlearningmario.mario.entity.World;
import fr.utbm.tc.qlearningmario.mario.ui.MarioGUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/** Main class of the application.
 *
 * @author Benoît CORTIER
 * @mavengroupid fr.utbm.tc
 * @mavenartifactid QLearningMario
 */
public class Game extends Application {
	public static final int SCENE_HEIGHT;

	public static final int SCENE_WIDTH;

	public static final int SCALE;

	private static final int NUMBER_OF_THREAD = 2;

	static {
		SCENE_WIDTH = Integer.parseInt(Locale.getString(Game.class, "scene.width")); //$NON-NLS-1$
		SCENE_HEIGHT = Integer.parseInt(Locale.getString(Game.class, "scene.height")); //$NON-NLS-1$
		SCALE = Integer.parseInt(Locale.getString(Game.class, "scene.scale")); //$NON-NLS-1$
	}

	private final Logger log = Logger.getLogger(Game.class.getName());

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Resources.getResource(getClass(), "fr/utbm/tc/qlearningmario/MainWindow.fxml")); //$NON-NLS-1$

			BorderPane root = (BorderPane) loader.load();

			Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

			primaryStage.setScene(scene);
			primaryStage.setTitle(Locale.getString(getClass(), "frame.title")); //$NON-NLS-1$

			Canvas canvas = new Canvas(SCENE_WIDTH, SCENE_HEIGHT);
			root.setCenter(canvas);

			GraphicsContext gc = canvas.getGraphicsContext2D();

			World world = new World();

			MarioGUI gui = new MarioGUI(gc);
			world.addWorldListener(gui);
			gui.start();

			Scheduler scheduler = new Scheduler(world);
			world.addWorldListener(scheduler);

			// Loading a level.
			URL resource = Resources.getResource(getClass(), "fr/utbm/tc/qlearningmario/levels/levelB.png"); //$NON-NLS-1$
			assert (resource != null);
			for (Entity<?> entity : LevelLoader.loadLevelFromImage(resource)) {
				world.addEntity(entity);
			}

			ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);

			// Run the scheduler.
			executor.execute(scheduler);
			executor.shutdown();

			primaryStage.show();

			primaryStage.setOnCloseRequest(
					(WindowEvent we) -> {
						this.log.info(Locale.getString(Game.this.getClass(), "closing.stage")); //$NON-NLS-1$
						scheduler.stop();
					});
		} catch (Exception e) {
			this.log.severe(e.toString());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
