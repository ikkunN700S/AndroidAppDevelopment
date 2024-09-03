package jp.ac.meijou.android.s231205183;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import jp.ac.meijou.android.s231205183.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private PrefDataStore prefDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefDataStore = PrefDataStore.getInstance(this);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ボタンクリック動作
        binding.button.setOnClickListener(view ->{
            // テキスト指定
            // binding.text.setText(R.string.text2);
            var text = binding.editTextText.getText().toString();
            binding.text.setText(text);

            // 画像指定
            var icon = ContextCompat.getDrawable(this, R.drawable.ic_android_red_24dp);
            binding.imageView.setImageDrawable(icon);
        });

        binding.button2.setOnClickListener((view -> {
            var text = binding.editTextText.getText().toString();
            prefDataStore.setString("name", text);
        }));

        // editTextTextが更新されたらテキストを変更する
        binding.editTextText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.text.setText(editable.toString());
            }
        });
    }

    // 立ち上がるとき
    protected void onStart(){
        super.onStart();
        prefDataStore.getString("name")
                .ifPresent((name -> binding.text.setText(name)));
    }

    // 閉じられるとき
    protected void onStop(){
        super.onStop();
        var text = binding.editTextText.getText().toString();
        prefDataStore.setString("name", text);
    }
}