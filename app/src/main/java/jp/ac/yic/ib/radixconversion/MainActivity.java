package jp.ac.yic.ib.radixconversion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         ビューのIDを取得
         */
        Spinner spn_before = findViewById(R.id.spn_before);
        Spinner spn_after = findViewById(R.id.spn_after);
        Button btn_ReplaceRadix = findViewById(R.id.btn_ReplaceRadix);
        Button btn_Convert = findViewById(R.id.btn_convert);
        EditText eT_InNum = findViewById(R.id.eT_InNum);
        TextView txt_result = findViewById(R.id.txt_result);
        TextView txt_info = findViewById(R.id.txt_info);


        /*
         プルダウンに要素を追加
         */
        String[] spinnerItems = {"2進数", "8進数", "10進数", "16進数"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_before.setAdapter(adapter);
        spn_after.setAdapter(adapter);


        /*
         変換ボタンの実装
         */
        btn_Convert.setOnClickListener( view -> {
            String radixBefore = spn_before.getSelectedItem().toString();
            String radixAfter = spn_after.getSelectedItem().toString();
            String InNum = eT_InNum.getText().toString();
            if (eT_InNum.getText().toString().equals("")) {
                txt_info.setText("数値が入力されていません。");
            } else if (! isInt(radixBefore, InNum)) {
                txt_info.setText("数値が有効ではありません。");
            } else {
                txt_info.setText("");
                int decimal = 0;
                switch (radixBefore) {
                    case "2進数":
                        decimal = Integer.parseInt(InNum, 2);
                        break;
                    case "8進数":
                        decimal = Integer.parseInt(InNum, 8);
                        break;
                    case "10進数":
                        decimal = Integer.parseInt(InNum);
                        break;
                    case "16進数":
                        decimal = Integer.parseInt(InNum, 16);
                }
                String converted = doConvert(decimal, radixAfter);
                txt_result.setText(converted);
            }
        });


        /*
         基数入れ替えボタンの実装
         */
        btn_ReplaceRadix.setOnClickListener(view -> {
            int positionBefore = spn_before.getSelectedItemPosition();
            int positionAfter = spn_after.getSelectedItemPosition();
            spn_before.setSelection(positionAfter);
            spn_after.setSelection(positionBefore);
            eT_InNum.setText("");
            txt_result.setText("0");
        });

    }


    /*
     2進数、8進数、16進数に変換
     */
    private String doConvert(int decimal, String radixAfter) {
        switch (radixAfter) {
            case "2進数":
                return Integer.toBinaryString(decimal);
            case "8進数":
                return Integer.toOctalString(decimal);
            case "10進数":
                return String.valueOf(decimal);
            case "16進数":
                return Integer.toHexString(decimal);
        }
        return "0";
    }


    /*
     入力値が数値かどうか判定
     */
    private boolean isInt(String radixBefore, String inNum) {
        switch (radixBefore) {
            case "2進数":
                try {
                    Integer.parseInt(inNum, 2);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            case "10進数":
                try {
                    Integer.parseInt(inNum);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            case "8進数":
                try {
                    Integer.parseInt(inNum, 8);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            case "16進数":
                try {
                    Integer.parseInt(inNum, 16);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
        }
        return false;
    }


}