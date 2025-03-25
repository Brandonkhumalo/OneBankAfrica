package com.techmania.onebankafrica.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.techmania.onebankafrica.R;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class AccountActivity extends AppCompatActivity {
    Button download;
    TextInputEditText email;

    String text = "Account Confirmation Email";
    String body = "Please find the attached PDF.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Toolbar toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        email = findViewById(R.id.confirmEmail);
        download = findViewById(R.id.btndownload);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString().trim();

                File pdfFile = createPdfFile("confirmation.pdf", text);
                if (pdfFile != null) {
                    sendEmail(userEmail, "PDF Attachment", body, pdfFile);
                }
                //sendEmail(userEmail, text, body);
            }
        });
    }
    public void sendEmail(final String to, final String subject, final String body, final File attachmentFile) {
        new SendEmailTask(to, subject, body, attachmentFile).execute();
    }

    private static class SendEmailTask extends AsyncTask<Void, Void, Boolean> {
        private final String to, subject, body;
        private final File attachment;
        private static final String FROM_EMAIL = "apex2.0predator@gmail.com";  // Sender email
        private static final String PASSWORD = "hrobguwmsqgjkswf";  // Use App Password from Google

        public SendEmailTask(String to, String subject, String body, File attachment) {
            this.to = to;
            this.subject = subject;
            this.body = body;
            this.attachment = attachment;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Properties properties = new Properties();
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");

                Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(FROM_EMAIL));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);

                //create message body
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(body);

                MimeBodyPart attachmentPart = new MimeBodyPart();
                FileDataSource source = new FileDataSource(attachment);
                attachmentPart.setDataHandler(new DataHandler(source));
                attachmentPart.setFileName(attachment.getName());

                MimeMultipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                multipart.addBodyPart(attachmentPart);
                message.setContent(multipart);

                Transport.send(message);
                return true;
            } catch (MessagingException e) {
                Log.e("email", "Email not sent", e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Log.d("email", "Email sent successfully!");
            } else {
                Log.e("email", "Failed to send email!");
            }
        }
    }

    private File createPdfFile(String fileName, String textContent) {
        try {
            File pdfFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);
            PdfWriter writer = new PdfWriter(new FileOutputStream(pdfFile));
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);
            document.add(new Paragraph(textContent));
            document.close();
            return pdfFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}









