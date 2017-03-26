package br.com.sample.threadsapp.models;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImageWeb {

    private static final String URL_IMAGE = "http://icon-icons.com/icons2/691/PNG/128/android_n_icon-icons.com_61479.png";
    private static Bitmap bitmap;

    /**
     * Realiza o download de uma imagem em uma outra Thread
     */
    public static void getImgWeb(final Activity activity, final ImageView imageView){
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Carregando imagem...");
        progressDialog.show();

        //  O Android não permite fazer esse acesso na Main Thread
        new Thread(){
            @Override
            public void run() {
                try{
                    //  Poderia acrescentar ainda uma String Url como parâmetro, e baixar qualquer imagem
                    URL url = new URL(URL_IMAGE);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }catch(IOException e){
                    e.printStackTrace();
                }
                //  Atualiza o componente de interface
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.setMessage("Imagem carregada!");
                        imageView.setImageBitmap(bitmap);
                        progressDialog.dismiss();
                    }
                });
            }
        }.start();
    }
}
