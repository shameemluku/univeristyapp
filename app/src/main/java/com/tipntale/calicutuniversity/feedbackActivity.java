package com.tipntale.calicutuniversity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class feedbackActivity extends AppCompatActivity {

    Button send;
    EditText txtName,txtMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        txtName = (EditText) findViewById(R.id.txtname);
        txtMessage = (EditText) findViewById(R.id.txtmessage);

        send = (Button) findViewById(R.id.sendfeedbtn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtName.length()==0)
                {
                    txtName.setError("Enter name");
                }
                else if(txtMessage.length()==0)
                {
                    txtMessage.setError("Enter some message");
                }
                else
                {
                    sendInfo();
                }

            }
        });
    }

    public void sendInfo()
    {
        String message = txtMessage.getText().toString();
        String name = txtName.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"shameem.lukman22@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback from "+name);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        intent.setType("message/rfc822");
        intent.setPackage("com.google.android.gm");
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,"Gmail app is not installed",Toast.LENGTH_SHORT).show();
        }

    }

    public void closeFeed(View v)
    {
        finish();
    }
}
