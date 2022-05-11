package com.deloitte.baseapp.utils;

import java.util.List;

public interface ICSVFileOnRead<T> {

    void onData(List<T> result);

}
