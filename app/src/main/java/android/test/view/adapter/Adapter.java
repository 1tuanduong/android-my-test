package android.test.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.test.R;
import android.test.model.KeyWord;
import android.test.utils.Helper;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    private ArrayList<KeyWord> keyWordsList;
    private Context mContext;

    public Adapter(Context mContext){
        this.keyWordsList = new ArrayList<>();
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, null, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.Holder holder, int position) {

        if (keyWordsList.size() > 0 && keyWordsList.get(position) != null){

                ArrayList<String> splitedContent = new ArrayList<>();
                splitedContent = Helper.spliteString(keyWordsList.get(position).getName());

                Helper.editString(splitedContent);
                holder.textView.setText(Helper.editString(splitedContent));

        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (keyWordsList != null && keyWordsList.size() > 0) {
            count = keyWordsList.size();
        }
        return count;
    }

    public void add(ArrayList<KeyWord> arrayList){
        keyWordsList = arrayList;
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView textView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
