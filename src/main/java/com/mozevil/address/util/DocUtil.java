package com.mozevil.address.util;

import com.mozevil.address.model.Person;
import org.docx4j.model.fields.merge.DataFieldName;
import org.docx4j.model.fields.merge.MailMerger;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocUtil {

    public static void createPersonDOCX(Person person) throws Docx4JException {
        String source_template = "tmpl/person.docx";
        String out_dir = "print/";
        String out_name = person.getFirstName() + person.getLastName() + ".docx";

        WordprocessingMLPackage template = WordprocessingMLPackage.load(new File(source_template));

        List<Map<DataFieldName, String>> data = new ArrayList<>();
        Map<DataFieldName, String> fieldsMap = new HashMap<>();

        fieldsMap.put(new DataFieldName("FIRST_NAME"), person.getFirstName());
        fieldsMap.put(new DataFieldName("LAST_NAME"), person.getLastName());
        fieldsMap.put(new DataFieldName("CITY"), person.getCity());
        fieldsMap.put(new DataFieldName("STREET"), person.getStreet());
        fieldsMap.put(new DataFieldName("POSTAL_CODE"), String.valueOf(person.getPostalCode()));
        fieldsMap.put(new DataFieldName("BIRTHDAY"), DateUtil.format(person.getBirthday()));

        data.add(fieldsMap);

        WordprocessingMLPackage output = MailMerger.getConsolidatedResultCrude(template, data);

        output.save(new File(out_dir + out_name));
    }
}
