package com.techmania.onebankafrica.Activities.ExtraActivities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PdfDownloader {
    private Context context;

    public PdfDownloader(Context context) {
        this.context = context;
    }

    public void savePdfToStorage(int rawResourceId, String fileName) {
        try {
            // Get the PDF file from res/raw folder
            InputStream inputStream = context.getResources().openRawResource(rawResourceId);
            File downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File pdfFile = new File(downloadsFolder, fileName);
            FileOutputStream outputStream = new FileOutputStream(pdfFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            Toast.makeText(context, "PDF saved to Downloads!", Toast.LENGTH_SHORT).show();

            // Open PDF with external viewer
            openPdfWithExternalViewer(pdfFile);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error saving file!", Toast.LENGTH_SHORT).show();
        }
    }

    private void openPdfWithExternalViewer(File file) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(Intent.createChooser(intent, "Open PDF with"));
    }
}
