package com.example.drawernavigationtabs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;



import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
 
public class CustomArrayAdapter extends ArrayAdapter<Menu> {

    Context context;
    List<Menu> objects;
    
    public CustomArrayAdapter(Context context, int textViewResourceId, List<Menu> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.objects = objects;
        
    }
    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        ImageView sideimageView;
        TextView txtMenuName;
        TextView txtMenuDesc;
        TextView txtPrice;
        TextView url;
        TextView date;
        TextView saving;
        TextView price;
        TextView startDate;
        TextView endDate;
    }

    @SuppressWarnings("deprecation")
	public View getView(int position, View convertView, ViewGroup parent) {
        
    	ViewHolder holder = null;
        Menu rowItem = getItem(position);
        
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        System.out.println("Type: "+rowItem.getType());
        if(rowItem.getType()=="NEWS"){
    		convertView = mInflater.inflate(R.layout.news_row, null);
    		holder = new ViewHolder();
            holder.txtMenuName = (TextView) convertView.findViewById(R.id.menu_name);
            holder.txtMenuDesc = (TextView) convertView.findViewById(R.id.description);
            holder.imageView = (ImageView) convertView.findViewById(R.id.list_image);
            holder.url = (TextView) convertView.findViewById(R.id.url);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            
            holder.txtMenuDesc.setText(rowItem.getDescription());
            holder.txtMenuName.setText(rowItem.getTitle());
            holder.url.setText(rowItem.getUrl());
            holder.date.setText(rowItem.getpubDate());
    		
    	}
    	else if(rowItem.getType()=="EVENTS"){
    		convertView = mInflater.inflate(R.layout.events_row, null);
    		holder = new ViewHolder();
            holder.txtMenuName = (TextView) convertView.findViewById(R.id.menu_name);
            holder.txtMenuDesc = (TextView) convertView.findViewById(R.id.description);
            holder.imageView = (ImageView) convertView.findViewById(R.id.list_image);
            holder.url = (TextView) convertView.findViewById(R.id.url);
            holder.startDate = (TextView) convertView.findViewById(R.id.startDate);
            holder.endDate = (TextView) convertView.findViewById(R.id.endDate);

            holder.txtMenuDesc.setText(rowItem.getDescription());
            holder.txtMenuName.setText(rowItem.getTitle());
            holder.url.setText(rowItem.getUrl());
            holder.startDate.setText("Starts: "+rowItem.getStartDate());
            holder.endDate.setText("Ends: "+rowItem.getEnddate());
            
    	}
    	else if(rowItem.getType()=="DEALS"){
    		convertView = mInflater.inflate(R.layout.deals_row, null);
    		holder = new ViewHolder();
            holder.txtMenuName = (TextView) convertView.findViewById(R.id.menu_name);
            holder.saving = (TextView) convertView.findViewById(R.id.saving);
            holder.imageView = (ImageView) convertView.findViewById(R.id.list_image);
            holder.url = (TextView) convertView.findViewById(R.id.url);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            
            String sav=rowItem.getSaving().toString();
            //String[] p = sav.split(" ");
            sav=sav.replaceAll("\\s\\s+", ",");
          //  System.out.println(sav);
            holder.saving.setText(sav);
            holder.txtMenuName.setText(rowItem.getTitle());
            holder.url.setText(rowItem.getUrl());
            holder.price.setText(rowItem.getPrice());
    	}
        //System.out.println("View"+convertView);
        //if (convertView == null) {
        	        	
        	
            
            //holder.sideimageView = (ImageView) convertView.findViewById(R.id.side_image);
            // holder. =  convertView.findViewById(R.id.type);
            
            //Drawable id = context.getResources().getDrawable(R.drawable.arrow);
            //holder.sideimageView.setImageResource(R.drawable.arrow);
       //     convertView.setTag(holder);
           
            Bitmap image=rowItem.getImage();
			if(image == null){
            	holder.imageView.setImageResource(R.drawable.rihanna);
            }
            else{
            	if(rowItem.getType()=="NEWS"){
            		System.out.println("Bitmap:"+ image);
            	}
            	holder.imageView.setImageBitmap(image);
            }
           
        //}
//         else{
//        	holder = (ViewHolder) convertView.getTag();
//        }
            
       
        convertView.setTag(holder); 
        
  //    System.out.println("hello"+rowItem.getTitle());
        return convertView;
    }
    
   
}