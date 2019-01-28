package org.resoft.domainnotifier;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by onuragtas on 15.07.2017.
 */

public interface GeneralCallbacks {
    void VolleyResponse(JSONObject data) throws JSONException;

    void VolleyError();

    void ArrayListResponse(ArrayList<String> data);
}

