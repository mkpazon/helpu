package com.mamaai.angelhack2017.util;

import com.mamaai.angelhack2017.ParseConstants;
import com.mamaai.angelhack2017.model.Skill;
import com.mamaai.angelhack2017.model.Worker;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by mkpazon on 01/07/2017.
 */

public class ParseConverter {
    private ParseConverter() {
    }

    public static Worker toWorker(ParseObject parseObject) {
        Worker worker = new Worker();
        worker.setId(parseObject.getObjectId());
        worker.setName(parseObject.getString(ParseConstants.Worker.FIELD_NAME));
        worker.setAge(parseObject.getInt(ParseConstants.Worker.FIELD_AGE));

        final ParseFile parseFileProfilePic = parseObject.getParseFile(ParseConstants.Worker.FIELD_PHOTO);
        if (parseFileProfilePic != null) {
            worker.setPhotoUrl(parseFileProfilePic.getUrl());
        }
        worker.setLocation(parseObject.getString(ParseConstants.Worker.FIELD_LOCATION));
        worker.setDescription(parseObject.getString(ParseConstants.Worker.FIELD_DESCRIPTION));
        List<String> credentials = parseObject.getList(ParseConstants.Worker.FIELD_CREDENTIALS);
        worker.setCredentials(credentials);
        return worker;
    }

    public static Skill toSkill(ParseObject parseObject) {
        Skill skill = new Skill();
        skill.setId(parseObject.getObjectId());
        skill.setPrice(parseObject.getDouble(ParseConstants.Skill.FIELD_PRICE));
        skill.setName(parseObject.getString(ParseConstants.Skill.FIELD_NAME));
        ParseObject parseWorker = parseObject.getParseObject(ParseConstants.Skill.FIELD_WORKER);
        Worker worker = toWorker(parseWorker);
        skill.setWorker(worker);
        return skill;
    }
}
