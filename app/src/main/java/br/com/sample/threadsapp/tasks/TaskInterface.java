package br.com.sample.threadsapp.tasks;

import android.graphics.Bitmap;

public interface TaskInterface {

    /**
     * Atualiza a nossa interface (Componente de layout),
     * Qualquer classe que vai quer alterar a UI, vai ter que implementar essa interface
      */
    void beforeDownload(Bitmap bitmap);

}
