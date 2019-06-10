package com.grupa1.teleman.objects;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grupa1.teleman.MainActivity;
import com.grupa1.teleman.R;

import java.sql.ResultSet;

public class Order {
    private int index, ID;
    private String description, state, location, destination;
    View view;
    LayoutInflater inflater;
    public Order(ResultSet resultSet, int index){
        try {
            ID              = resultSet.getInt("ID");
            description     = resultSet.getString("Description");
            location = resultSet.getString("Location");
            destination = resultSet.getString("Destination");
            state           = resultSet.getString("State");
            this.index=index;

            //inflater = (LayoutInflater) MainActivity.getAppContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //view = inflater.inflate(R.layout.custom_task_full, null);
            view = MainActivity.getInflater().inflate(R.layout.custom_task_full, null);

            TextView nmbr = ((TextView)view.findViewById(R.id.taskNumber_textView));
            TextView msg = ((TextView)view.findViewById(R.id.taskMessage_textView));
            TextView loc = ((TextView)view.findViewById(R.id.location_textView));
            TextView dest = ((TextView)view.findViewById(R.id.destination_textView));
            TextView st = ((TextView)view.findViewById(R.id.taskState_textView));

            nmbr.setText(""+ID);
            msg.setText(description);
            loc.setText(location);
            dest.setText(destination);
            st.setText(state);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public View getView(){
        return view;
    }

    public String getState(){
        return state;
    }

    public int getIndex(){
        return index;
    }
}
