package com.mamaai.angelhack2017.util;

import com.mamaai.angelhack2017.ParseConstants;
import com.mamaai.angelhack2017.model.Worker;
import com.parse.ParseObject;

/**
 * Created by mkpazon on 01/07/2017.
 */

public class ParseConverter {
    private ParseConverter() {

    }

    public static Worker toWorker (ParseObject parseObject) {
        Worker worker = new Worker();
        worker.setName(parseObject.getString(ParseConstants.Worker.FIELD_NAME));
        worker.setAge(parseObject.getInt(ParseConstants.Worker.FIELD_AGE));

        // TODO
//        ListparseObject.getList(ParseConstants.Worker.FIELD_SKILLS)
//        worker.setSkills();
        return worker;
    }
}
