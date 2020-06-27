package com.example.flightmobileapp;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class URLDatabaseDao_Impl implements URLDatabaseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<myURL> __insertionAdapterOfmyURL;

  private final SharedSQLiteStatement __preparedStmtOfRemoveOldest;

  private final SharedSQLiteStatement __preparedStmtOfSetLastUsed;

  public URLDatabaseDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfmyURL = new EntityInsertionAdapter<myURL>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `saved_urls` (`urlID`,`url`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, myURL value) {
        stmt.bindLong(1, value.getUrlID());
        if (value.getUrl() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUrl());
        }
      }
    };
    this.__preparedStmtOfRemoveOldest = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM saved_urls WHERE urlID = (SELECT MIN(urlID) from saved_urls)";
        return _query;
      }
    };
    this.__preparedStmtOfSetLastUsed = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE saved_urls SET urlID = (SELECT MAX(urlID) FROM saved_urls) + 1 WHERE url = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final myURL url) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfmyURL.insert(url);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void removeOldest() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfRemoveOldest.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfRemoveOldest.release(_stmt);
    }
  }

  @Override
  public void setLastUsed(final String url) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfSetLastUsed.acquire();
    int _argIndex = 1;
    if (url == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, url);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfSetLastUsed.release(_stmt);
    }
  }

  @Override
  public myURL getByURL(final String url) {
    final String _sql = "SELECT * FROM saved_urls WHERE url = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (url == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, url);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUrlID = CursorUtil.getColumnIndexOrThrow(_cursor, "urlID");
      final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
      final myURL _result;
      if(_cursor.moveToFirst()) {
        final long _tmpUrlID;
        _tmpUrlID = _cursor.getLong(_cursorIndexOfUrlID);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        _result = new myURL(_tmpUrlID,_tmpUrl);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<myURL> getRelevants(final int amount) {
    final String _sql = "SELECT * FROM saved_urls ORDER BY urlID DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, amount);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUrlID = CursorUtil.getColumnIndexOrThrow(_cursor, "urlID");
      final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
      final List<myURL> _result = new ArrayList<myURL>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final myURL _item;
        final long _tmpUrlID;
        _tmpUrlID = _cursor.getLong(_cursorIndexOfUrlID);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        _item = new myURL(_tmpUrlID,_tmpUrl);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int size() {
    final String _sql = "SELECT COUNT(urlID) from saved_urls";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
