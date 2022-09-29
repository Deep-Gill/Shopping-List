package main.ui;

import java.util.EventListener;

public interface DetailListener extends EventListener {
    void detailEventOccured(DetailEvent event);
}
