package br.com.sample.threadsapp.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Param 1 - É o parâmetro de entrada do método doInBackgroud()
 * Param 2 - Parâmetro de entrada do método onProgressUpdate()
 * Param 2 - Valor de retorno do doInBackground, e parâmetro de entrada do onPostExecute()
 * Não é aceito tipos primitivos, é só classe (String, Integer, Double, Boolean...)
 */
public class Task extends AsyncTask<String, String, Bitmap>{

    private static Bitmap bitmap;
    private ProgressDialog progressDialog;
    private TaskInterface taskInterface;
    private Context context;

    public Task(Context context, TaskInterface taskInterface){
        this.context = context;
        this.taskInterface = taskInterface;
    }

    /**
     * Ainda está com acesso a Main Thread (Thread Principal)
     */
    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
    }

    /**
     * Está com acesso a outra Thread (secundária)
     * Sempre deve retornar alguma coisa
     */
    @Override
    protected Bitmap doInBackground(String... imageURL) {
        try{
            //  Altera o texto do onProgressUpdate()
            publishProgress("Carregando imagem...");

            //  Para acessar, usa anotaçao de array
            URL url = new URL(imageURL[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            publishProgress("Imagem carregada!");
        }catch(IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * Permite atualizar a UI do usuário, equivalente ao método runOnUiThread
     * publishProgress para alterar o parâmetro
     */
    @Override
    protected void onProgressUpdate(String... params) {
        progressDialog.setMessage(params[0]);
    }

    /**
     * Já está novamente com acesso a Thread Principal
     * Só é chamado quando o doInBackground retorna alguma coisa
     */
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        taskInterface.beforeDownload(bitmap);
        progressDialog.dismiss();
    }

}
