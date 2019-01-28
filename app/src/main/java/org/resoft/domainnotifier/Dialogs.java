package org.resoft.domainnotifier;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;

import java.util.ArrayList;


/**
 * Created by onuragtas on 23.03.2018.
 */

public class Dialogs {

    public void DialogAddNew(Activity c, final GeneralCallbacks cb) throws JSONException {
        final SessionManager session = new SessionManager(c);
        final Api api = new Api(c);

        final Dialog dialog = new Dialog(c);
        dialog.setContentView(R.layout.adddialog);

        final EditText title = (EditText) dialog.findViewById(R.id.title);


        Button dialogButton = (Button) dialog.findViewById(R.id.add);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    session.setDomain(title.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    cb.ArrayListResponse(session.getDomains());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                try {
                    cb.ArrayListResponse(session.getDomains());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        dialog.show();
    }
}
