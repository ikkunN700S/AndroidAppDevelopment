package jp.ac.meijou.android.s231205183;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Optional;

import jp.ac.meijou.android.s231205183.databinding.ActivityMain3Binding;

public class MainActivity3 extends AppCompatActivity {
    private ActivityMain3Binding binding;
    private final ActivityResultLauncher<Intent> getActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                switch (result.getResultCode()){
                    case RESULT_OK -> {
                        // OKボタンが押された
                        Optional.ofNullable(result.getData())
                                .map(data -> data.getStringExtra("ret"))
                                .map(text -> "Result: " + text)
                                .ifPresent(text -> binding.textResult.setText(text));
                    }
                    case RESULT_CANCELED -> {
                        // Cancelボタンが押された
                        binding.textResult.setText("Result: Canceled");
                    }
                    default -> {
                        // 想定外のことが起きた
                        binding.textResult.setText("Result: Unknown(" + result.getResultCode() + ")");
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 明示的Intent
        binding.buttonA.setOnClickListener(view ->{
            var intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        // 暗黙的Intent
        binding.buttonB.setOnClickListener(view ->{
            var intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.yahoo.co.jp"));
            startActivity(intent);
        });

        // 送信ボタン
        binding.button3.setOnClickListener(view ->{
            var intent = new Intent(this, MainActivity.class);
            var text = binding.editTextText2.getText().toString();
            intent.putExtra("data", text);
            startActivity(intent);
        });

        // 起動ボタン
        binding.startbutton.setOnClickListener(view ->{
            var intent = new Intent(this, MainActivity.class);
            // MainActivityから帰ってきたときにgetActivityResultで処理をする
            getActivityResult.launch(intent);
        });

    }
}