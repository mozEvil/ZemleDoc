package com.mozevil.address.util;

import com.mozevil.address.model.Person;
import javafx.concurrent.Task;

public class DocTask extends Task<Void> {

    private Person person;

    public DocTask(Person person) {
        this.person = person;
    }

    @Override
    protected Void call() throws Exception {
        DocUtil.createPersonDOCX(this.person);
        return null;
    }
}
