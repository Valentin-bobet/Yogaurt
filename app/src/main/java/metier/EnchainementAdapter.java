package metier;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vbobet.yogaurt.R;

import java.util.List;

/**
 * Created by vbobet on 20/10/2017.
 */

public class EnchainementAdapter extends BaseAdapter {

    private List<Enchainement> listEnchainement;
    private LayoutInflater inflater;

    public EnchainementAdapter(Context context, List<Enchainement> listE){
        this.inflater=LayoutInflater.from(context);
        this.listEnchainement=listE;
    }

    public int getCount(){
        return this.listEnchainement.size();
    }

    public Enchainement getItem(int rang){
        return this.listEnchainement.get(rang);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView posture;
        TextView nbResp;
        if(convertView==null){
            convertView = this.inflater.inflate(R.layout.vue_enchainement,parent,false);
            posture = convertView.findViewById(R.id.txtPosture);
            nbResp = convertView.findViewById(R.id.txtRespi);
            convertView.setTag(R.id.txtPosture,posture);
            convertView.setTag(R.id.txtRespi,nbResp);
        }
        else{
            posture = (TextView) convertView.getTag(R.id.txtPosture);
            nbResp = (TextView) convertView.getTag(R.id.txtRespi);
        }
        Enchainement exo = this.getItem(position);
        posture.setText(exo.getPosture());
        nbResp.setText(String.valueOf(exo.getNbResp()));
        return convertView;
    }
}
