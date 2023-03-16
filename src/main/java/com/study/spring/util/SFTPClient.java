package com.study.spring.util;

import com.jcraft.jsch.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.*;

public class SFTPClient implements AutoCloseable {
    private final Logger logger = LoggerFactory.getLogger(SFTPClient.class);

    private final Session session;
    private final Channel channel;
    private final ChannelSftp channelSftp;

    public SFTPClient(String host, String userName, String password, int port) throws JSchException {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(userName, host);
            session.setPassword(password);
            session.setPort(port);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            logger.error("sftp init Error[{}]", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 하나의 파일을 업로드 한다.
     *
     * @param sftpPath               저장될 경로(서버)
     * @param targetFileNameWithPath 경로를 포함한 저장할 파일 이름
     */
    public void upload(String sftpPath, String targetFileNameWithPath) {
        try (FileInputStream in = new FileInputStream(targetFileNameWithPath)) {
            upload(sftpPath, in, targetFileNameWithPath);
        } catch (FileNotFoundException e) {
            logger.error("sftp upload Error(Can not find file)[{}]", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("sftp upload Error[{}]", e.getMessage(), e);
        }
    }

    private void upload(String sftpPath, InputStream in, String targetFileNameWithPath) throws SftpException {
        SftpATTRS attrs = stat(sftpPath);

        if (Objects.nonNull(attrs)) {
            logger.info("Directory exists IsDir=" + attrs.isDir());
        } else {
            logger.info("Creating dir : " + sftpPath);
            channelSftp.mkdir(sftpPath);
        }

        channelSftp.cd(sftpPath);
        channelSftp.put(in, sftpPath + targetFileNameWithPath);
    }

    public void upload(String sftpPath, MultipartFile multipartFile, String targetFileNameWithPath) {
        try (InputStream in = multipartFile.getInputStream()) {
            upload(sftpPath, in, targetFileNameWithPath);
        } catch (FileNotFoundException e) {
            logger.error("sftp upload Error(Can not find file)[{}]", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("sftp upload Error[{}]", e.getMessage(), e);
        }
    }

    public void uploadFromUrl(String sftpPath, String urlString, String targetFileNameWithPath) {
        try (InputStream in = new URL(urlString).openStream()) {
            upload(sftpPath, in, targetFileNameWithPath);
        } catch (FileNotFoundException e) {
            logger.error("sftp upload Error(Can not find file)[{}]", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("sftp upload Error[{}]", e.getMessage(), e);
        }
    }

    private SftpATTRS stat(String path) throws SftpException {
        try {
            return channelSftp.stat(path);
        } catch (SftpException e) {
            logger.info(path + " : not found");
            throw e;
        }
    }

    /**
     * 하나의 파일을 다운로드 한다.
     *
     * @param sftpPath       저장된 경로(서버)
     * @param targetFileName 다운로드할 파일 이름
     * @param downloadPath   저장될 공간
     */
    public void download(String sftpPath, String targetFileName, String downloadPath) {
        try {
            channelSftp.cd(sftpPath);

            try (InputStream in = channelSftp.get(targetFileName);
                    FileOutputStream out = new FileOutputStream(downloadPath)) {
                IOUtils.copy(in, out);
            }
        } catch (SftpException | IOException e) {
            logger.error("sftp download Error[{}]", e.getMessage(), e);
        }
    }

    @Override
    public void close() throws Exception {
        channelSftp.exit();
        channel.disconnect();
        session.disconnect();
    }
}
