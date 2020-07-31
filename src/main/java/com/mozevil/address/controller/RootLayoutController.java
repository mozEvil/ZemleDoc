package com.mozevil.address.controller;

import com.mozevil.address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioMenuItem;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Контроллер для корневого макета. Корневой макет предоставляет базовый
 * макет приложения, содержащий строку меню и место, где будут размещены
 * остальные элементы JavaFX.
 *
 * @author Marco Jakob
 */
public class RootLayoutController {

    // Ссылка на главное приложение
    private MainApp mainApp;

    /**
     * Вызывается главным приложением, чтобы оставить ссылку на самого себя.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Создаёт пустую адресную книгу.
     */
    @FXML
    private void handleNew() {
        checkForUnsavedData();

        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    /**
     * Открывает FileChooser, чтобы пользователь имел возможность
     * выбрать адресную книгу для загрузки.
     */
    @FXML
    private void handleOpen() {
        checkForUnsavedData();

        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Задаем начальную директорию - место последнего сохранения или директория по умолчанию
        File initDir = mainApp.getPersonFilePath();
        if (initDir != null) {
            fileChooser.setInitialDirectory(initDir.getParentFile());
        } else {
            fileChooser.setInitialDirectory(new File("saves"));
        }

        // Показываем диалог загрузки файла
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }
    }

    /**
     * Сохраняет файл в файл адресатов, который в настоящее время открыт.
     * Если файл не открыт, то отображается диалог "save as".
     */
    @FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Открывает FileChooser, чтобы пользователь имел возможность
     * выбрать файл, куда будут сохранены данные
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Задаем начальную директорию - место последнего сохранения или директория по умолчанию
        File initDir = mainApp.getPersonFilePath();
        if (initDir != null) {
            fileChooser.setInitialDirectory(initDir.getParentFile());
        } else {
            fileChooser.setInitialDirectory(new File("saves"));
        }

        // Показываем диалог сохранения файла
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Проверяет корректность расширения
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
    }

    /**
     * Открывает диалоговое окно about.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("AddressApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Marco Jakob\nWebsite: http://code.makery.ch");

        alert.showAndWait();
    }

    /**
     * Закрывает приложение.
     */
    @FXML
    private void handleExit() {
        checkForUnsavedData();

        System.exit(0);
    }

    /**
     * Открывает статистику дней рождений.
     */
    @FXML
    private void handleShowBirthdayStatistics() {
        mainApp.showBirthdayStatistics();
    }

    /**
     * Проверяет, есть ли не сохраненные изменения текущих данных.
     * Если есть, то открывает диалоговое окно с вопросом: сохранить или нет?
     * При выборе "да" - вызывает метод сохранения данных.
     * */
    private void checkForUnsavedData() {
        if (mainApp.wasModified()) {
            //TODO: диалоговое окно, сохранить последние изменения? да/нет
        }
    }

    /**
     * Меняет стиль интерфейса прграммы на css/DarkTheme.css
     * */
    @FXML
    private void handleDarkStyle() {
        mainApp.setStyle("/css/DarkTheme.css");
    }

    /**
     * Меняет стиль интерфейса прграммы на css/LightTheme.css
     * */
    @FXML
    private void handleLightStyle() {
        mainApp.setStyle("/css/LightTheme.css");
    }
}