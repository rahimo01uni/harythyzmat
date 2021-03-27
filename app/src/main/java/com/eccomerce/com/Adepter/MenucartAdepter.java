package com.eccomerce.com.Adepter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eccomerce.com.Db;
import com.eccomerce.com.R;
import com.eccomerce.com.model.ItemModel;


import java.util.HashMap;
import java.util.List;
/**
 * Created by admin on 03/12/2016.
 */

public class MenucartAdepter extends  ArrayAdapter<ItemModel> {
    Db db;
    HashMap<String,String> details;
    private List<ItemModel> data;
    private Context context;

    int iqty=0,order=0;

    /*************  CustomAdapter Constructor *****************/
    public MenucartAdepter(Context ctx,List<ItemModel> dataList) {
        super(ctx, R.layout.item_row,dataList);
        this.context = ctx;
        this.data=dataList;
        db=new Db(ctx);
    }
    customButtonListener customListner;
    public interface customButtonListener {
        public void onButtonClickListner(int position, String value, String btn, HashMap<String, String> details);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(final int position, View convertView, ViewGroup parent) {
        Dataholder dataholder = new Dataholder();
       // final CartData cartdata = CartData.getInstance();
        // First let's verify the convertView is not null
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_row_cart_view, parent, false);
            details=new HashMap<>();
            dataholder.txtName = (TextView) convertView.findViewById(R.id.name);
            dataholder.txprice=(TextView)convertView.findViewById(R.id.price);
            dataholder.qty = (TextView)convertView.findViewById(R.id.textView2);
            dataholder.add=(Button)convertView.findViewById(R.id.button);
            dataholder.plus=(Button)convertView.findViewById(R.id.button4);
            dataholder.minus=(Button)convertView.findViewById(R.id.btnPay_confirm);
            dataholder.item_type_image=(ImageView) convertView.findViewById(R.id.item_type_image);
            convertView.setTag(dataholder);
        }else {
            dataholder= (Dataholder) convertView.getTag();
        }

        if(data.size()<=0)
        {
            dataholder.txtName.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/

            final ItemModel tempValues = data.get( position );

           // Log.e("tempvalues",tempValues.getItemArray_position()+"");
            /************  Set Model values in Holder elements ***********/
            final Dataholder finalDataholder = dataholder;
            finalDataholder.add.setTag(tempValues.getItemId());
            finalDataholder.plus.setTag(tempValues.getItemId());
            finalDataholder.minus.setTag(tempValues.getItemId());

            finalDataholder.txtName.setText( tempValues.getName() );
            finalDataholder.txprice.setText("Rs. "+ tempValues.getPrice() );
//            txtLeaveType.setText( tempValues.getLeaveType() );
//            txtLeaveStatus.setText( tempValues.getLeaveStatus() );
            int id = Integer.parseInt(tempValues.getItemId().toString());



            if(db.check(id)){
                finalDataholder.plus.setVisibility(View.VISIBLE);
                finalDataholder.minus.setVisibility(View.VISIBLE);
                finalDataholder.qty.setVisibility(View.VISIBLE);
                finalDataholder.add.setVisibility(View.INVISIBLE);
                iqty = db.display_qty_item(id);
                finalDataholder.qty.setText("" + iqty);
            }else {
                finalDataholder.plus.setVisibility(View.INVISIBLE);
                finalDataholder.minus.setVisibility(View.INVISIBLE);
                finalDataholder.qty.setVisibility(View.INVISIBLE);
                finalDataholder.add.setVisibility(View.VISIBLE);
            }

            finalDataholder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    details.put("LeaveID", tempValues.getItemId());
                    int id = Integer.parseInt(v.getTag().toString());
                    if (customListner != null) {
                        Log.e("ID", "" + id);
                        db.update_plus(id, Integer.parseInt(tempValues.getPrice()), tempValues.getName());
                        customListner.onButtonClickListner(position, v.getTag().toString(), "plus", details);
                    }
                    iqty=db.display_qty_item(id);
                  //  iqty = cartdata.display_customiz(id);
                    finalDataholder.qty.setText("" + iqty);
                }
            });

            finalDataholder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    details.put("LeaveID", tempValues.getItemId());

                    int id = Integer.parseInt(v.getTag().toString());
                    if (customListner != null) {
                        Log.e("ID", "" + id);
                        db.update_minus(id, Integer.parseInt(tempValues.getPrice()), tempValues.getName());
                        iqty=db.display_qty_item(id);
                        if (iqty == 0){
                            data.remove(position);
                            finalDataholder.plus.setVisibility(View.INVISIBLE);
                            finalDataholder.minus.setVisibility(View.INVISIBLE);
                            finalDataholder.qty.setVisibility(View.INVISIBLE);
                            finalDataholder.add.setVisibility(View.INVISIBLE);
                            db.deletrow(id);
                            notifyDataSetChanged();
                            customListner.onButtonClickListner(position, v.getTag().toString(), "minus", details);

                        }
                       // notifyDataSetChanged();
                        finalDataholder.qty.setText("" + iqty);
                    }
                    notifyDataSetChanged();

                }
            });
        }
        return convertView;
    }

    static class Dataholder{
        public Button add,plus,minus;
        public TextView qty,txtName,txprice;
        ImageView item_type_image;

    }
}

