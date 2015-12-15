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

package fr.utbm.tc.qlearningmario.mario.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import org.arakhne.afc.vmutil.locale.Locale;

import fr.utbm.tc.qlearningmario.mario.Scheduler;
import fr.utbm.tc.qlearningmario.mario.agent.MarioAgent;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

@SuppressWarnings({ "static-method", "unused" })
public class MainController {
	public static Stage primaryStage;

	public static Scheduler scheduler;

	private URL currentFileURL;

	private Logger logger = Logger.getLogger(MainController.class.toString());

	public void handleCloseAction(ActionEvent event) {
		primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	public void handleOpenFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(Locale.getString(this.getClass(), "fileChooser.window.title.load")); //$NON-NLS-1$
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("AI", "*.serai")); //$NON-NLS-1$ //$NON-NLS-2$


		scheduler.pause();

		File file = fileChooser.showOpenDialog(primaryStage);

		if (file != null) {
			try {
				this.currentFileURL = file.toURI().toURL();
				enableSaveMenuItem();

				MarioAgent marioAgent = scheduler.getMarioAgent();
				if (marioAgent != null) {
					try {
						marioAgent.loadQProblem(this.currentFileURL);
					} catch (ClassNotFoundException e) {
						this.logger.severe(e.getMessage());
					}
				}
			} catch (IOException e) {
				this.logger.severe(e.getMessage());
			}
		}

		scheduler.unpause();
	}

	public void handleSaveFile(ActionEvent event) {
		if (this.currentFileURL != null) {
			MarioAgent marioAgent = scheduler.getMarioAgent();
			if (marioAgent != null) {
				scheduler.pause();

				try {
					marioAgent.saveQProblem(this.currentFileURL);
				} catch (IOException e) {
					this.logger.severe(e.getMessage());
				}

				scheduler.unpause();
			}
		}
	}

	public void handleSaveAsFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(Locale.getString(this.getClass(), "fileChooser.window.title.save")); //$NON-NLS-1$
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("AI", "*.serai")  //$NON-NLS-1$ //$NON-NLS-2$
				);

		scheduler.pause();

		File file = fileChooser.showSaveDialog(primaryStage);

		if (file != null) {
			try {
				this.currentFileURL = file.toURI().toURL();
				enableSaveMenuItem();

				MarioAgent marioAgent = scheduler.getMarioAgent();
				if (marioAgent != null) {
					marioAgent.saveQProblem(this.currentFileURL);
				}
			} catch (IOException e) {
				this.logger.severe(e.getMessage());
			}
		}

		scheduler.unpause();
	}

	private void enableSaveMenuItem() {
		MenuBar menuBar = (MenuBar) primaryStage.getScene().lookup("#mainMenuBar"); //$NON-NLS-1$
		menuBar.getMenus().get(0).getItems().get(1).setDisable(false);
	}
}
