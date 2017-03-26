package br.com.sample.threadsapp.activities;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import br.com.sample.threadsapp.R;
import br.com.sample.threadsapp.models.DownloadImageWeb;
import br.com.sample.threadsapp.tasks.Task;
import br.com.sample.threadsapp.tasks.TaskInterface;

public class ImageDownloadActivity extends AppCompatActivity implements TaskInterface{

    private ImageView iv_downloaded;
    private static final String URL_IMAGE = "http://icon-icons.com/icons2/691/PNG/128/android_n_icon-icons.com_61479.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_download);

        iv_downloaded = (ImageView) findViewById(R.id.iv_downloaded);
    }

    public void imageDownload(View view){
        DownloadImageWeb.getImgWeb(ImageDownloadActivity.this, iv_downloaded);
    }

    public void imageDownloadAsyncTask(View view) {
        //  Istancia a classe Task para poder usar o AsyncTask
        Task task = new Task(this, this);

        //  Executa o AsyncTask (Parâmetro de entrada, minha URL)
        //  Poderia ter mais de um parâmetro de entrada, era só alterar no doInBackground e acrescentar mais
        task.execute(URL_IMAGE);
    }

    /**
     * Atualiza a interface do usuário
     */
    @Override
    public void beforeDownload(Bitmap bitmap) {
        iv_downloaded.setImageBitmap(bitmap);
    }
}
