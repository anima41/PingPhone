package com.redandborder.pingphone.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailUtil extends AsyncTask<String, Integer, Long> {
    Context context;
    public MailUtil(Context context){
        this.context = context;
    }

    String email = "###@gmail.com";
    String password = "###";
    String body = "これがメールの本文になります";
    String subject = "これがメールの件名になります";

    private void sendMail(Context context) {

        try {
            //email  password update
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            sp.edit().putString("email", email).commit();
            sp.edit().putString("password", password).commit();

            //以下メール送信
            final Properties property = new Properties();
            property.put("mail.smtp.host",                "smtp.gmail.com");
            property.put("mail.host",                     "smtp.gmail.com");
            property.put("mail.smtp.port",                "465");
            property.put("mail.smtp.socketFactory.port",  "465");
            property.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            // セッション
            final Session session = Session.getInstance(property, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            });

            MimeMessage mimeMsg = new MimeMessage(session);

            mimeMsg.setSubject(subject, "utf-8");
            mimeMsg.setFrom(new InternetAddress(email));
            mimeMsg.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(email));

            final MimeBodyPart txtPart = new MimeBodyPart();
            txtPart.setText(body, "utf-8");

            final Multipart mp = new MimeMultipart();
            mp.addBodyPart(txtPart);
            //mp.addBodyPart(filePart); //添付ファイルをする場合はこれ
            mimeMsg.setContent(mp);

            // メール送信する。
            final Transport transport = session.getTransport("smtp");
            transport.connect(email,password);
            transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
            transport.close();

        } catch (MessagingException e) {
            Log.e(this.getClass().getName(),e.getMessage());

        } catch (Exception e) {
            Log.e(this.getClass().getName(),e.getMessage());
        } finally {
            System.out.println("finish sending email");
        }
    }

    @Override
    protected Long doInBackground(String... params) {
        sendMail(this.context);
        return null;
    }
}
