package com.tl.common.directorywatcher;

import java.io.File;

import com.tl.common.directorywatcher.BaseListener;
import com.tl.common.directorywatcher.IFileListener;

public class FileListener extends BaseListener implements IFileListener {


    /**
     * Connstructor
     */
    public FileListener() {
        super();
    }

    public void onStart(Object monitoredResource) {
        // On startup
        if (monitoredResource instanceof File) {
            File resource = (File) monitoredResource;
            if (resource.isDirectory()) {

                System.out.println("��ʼ��أ�     " + resource.getAbsolutePath());

                File[] files = resource.listFiles();
                for (int i = 0; i < files.length; i++) {
                    File f = (File) files[i];
                    onAdd(f);
                }
            }
        }
    }

    public void onStop(Object notMonitoredResource) {

    }

    public void onAdd(Object newResource) {
        if (newResource instanceof File) {
            File file = (File) newResource;
            if (file.isFile()) {
            	//String s = file.getAbsolutePath();
            	//String a[] = s.split("\\\\");
                System.out.println("�ļ���" + file.getAbsolutePath() + " ���");
            }
        }
    }

    public void onChange(Object changedResource) {
        if (changedResource instanceof File) {
            File file = (File) changedResource;
            if (file.isFile()) {
                System.out.println("�ļ���" + file.getAbsolutePath() + " �޸�");
            }

        }
    }

    public void onDelete(Object deletedResource) {
        if (deletedResource instanceof String) {
            String deletedFile = (String) deletedResource;
            System.out.println("�ļ���"+deletedFile + " ɾ��");
        }
    }
}

