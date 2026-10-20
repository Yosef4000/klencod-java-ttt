package com.example.myapp;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<FoodItem> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // قائمة الوجبات والتوصيل
        foodList = new ArrayList<>();
        foodList.add(new FoodItem("بيتزا مارجريتا", "بيتزا طازجة بصلصة الطماطم وجبنة الموزاريلا", 12.50, android.R.drawable.ic_menu_gallery));
        foodList.add(new FoodItem("برجر لحم مشوي", "برجر لحم مع الجبن والخس والصلصة الخاصة", 9.00, android.R.drawable.ic_menu_gallery));
        foodList.add(new FoodItem("وجبة شاورما", "شاورما دجاج ممتازة مع الثوم والبطاطس", 7.50, android.R.drawable.ic_menu_gallery));

        foodAdapter = new FoodAdapter(foodList, item -> {
            Toast.makeText(MainActivity.this, "تم إضافة " + item.getName() + " إلى الطلبات!", Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(foodAdapter);
    }

    // ==========================================
    // 1. Class نموذج بيانات الوجبة (FoodItem)
    // ==========================================
    public static class FoodItem {
        private String name;
        private String description;
        private double price;
        private int imageResId;

        public FoodItem(String name, String description, double price, int imageResId) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.imageResId = imageResId;
        }

        public String getName() { return name; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getImageResId() { return imageResId; }
    }

    // ==========================================
    // 2. Class محول البيانات (FoodAdapter)
    // ==========================================
    public static class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

        public interface OnItemClickListener {
            void onOrderClick(FoodItem item);
        }

        private List<FoodItem> foodList;
        private OnItemClickListener listener;

        public FoodAdapter(List<FoodItem> foodList, OnItemClickListener listener) {
            this.foodList = foodList;
            this.listener = listener;
        }

        @NonNull
        @Override
        public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
            return new FoodViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
            FoodItem item = foodList.get(position);
            holder.tvName.setText(item.getName());
            holder.tvDescription.setText(item.getDescription());
            holder.tvPrice.setText("$" + item.getPrice());
            holder.imgFood.setImageResource(item.getImageResId());

            holder.btnOrder.setOnClickListener(v -> listener.onOrderClick(item));
        }

        @Override
        public int getItemCount() {
            return foodList.size();
        }

        public static class FoodViewHolder extends RecyclerView.ViewHolder {
            ImageView imgFood;
            TextView tvName, tvDescription, tvPrice;
            Button btnOrder;

            public FoodViewHolder(@NonNull View itemView) {
                super(itemView);
                imgFood = itemView.findViewById(R.id.imgFood);
                tvName = itemView.findViewById(R.id.tvName);
                tvDescription = itemView.findViewById(R.id.tvDescription);
                tvPrice = itemView.findViewById(R.id.tvPrice);
                btnOrder = itemView.findViewById(R.id.btnOrder);
            }
        }
    }
}