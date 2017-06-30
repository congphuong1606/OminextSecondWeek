package picdesignbeauty.android.vn.picdesign.PicDesginAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import picdesignbeauty.android.vn.picdesign.Model.PicDesgin;
import picdesignbeauty.android.vn.picdesign.R;

/**
 * Created by MyPC on 29/06/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHoder>{
    ArrayList<PicDesgin> picDesgins=new ArrayList<>();

    public RecyclerAdapter(Context baseContext, int row_layout, ArrayList<PicDesgin> picDesgins) {
        this.picDesgins = picDesgins;
    }



    @Override
    public MyViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        return new MyViewHoder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHoder holder, int position) {
        final PicDesgin picDesgin = picDesgins.get(position);

        try {
            byte[] outImage = picDesgin.getImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            holder.imageView.setImageBitmap(theImage);
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return picDesgins.size();
    }

    public static class MyViewHoder extends RecyclerView.ViewHolder{
ImageView imageView;
        public MyViewHoder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.album);
        }
    }
}
