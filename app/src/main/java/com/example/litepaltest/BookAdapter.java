package com.example.litepaltest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017-2-24.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>{
    private List<Bookview> mBookList;

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener)
    {
        this.mOnItemClickListener=mOnItemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View bookview;
        TextView bookName;
        TextView bookAuthor;
        TextView bookId;

        public ViewHolder(View view){
            super(view);
            bookview=view;
            bookName=(TextView)view.findViewById(R.id.book_name);
            bookAuthor=(TextView)view.findViewById(R.id.book_author);
            bookId=(TextView)view.findViewById(R.id.book_id);
        }
    }

    public BookAdapter(List<Bookview> BookList){
        mBookList=BookList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.bookview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Bookview bookviewt=mBookList.get(position);
                //Toast.makeText(v.getContext(),"you clicked book name: "+bookviewt.getName(),Toast.LENGTH_SHORT).show();

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Bookview bookview=mBookList.get(position);
        holder.bookName.setText(bookview.getName());
        holder.bookAuthor.setText(bookview.getAuthor());
        //String bookIdt="bookview.getId()";
        holder.bookId.setText(" "+bookview.getId());

        if (mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,pos);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    public void removeData(int position) {
        mBookList.remove(position);
        notifyItemRemoved(position);
    }
}
