package com.example.mykotlinapp;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `user_table` (`id`,`fullName`,`isPrivate`,`login`,`avatarUrl`,`type`,`description`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getId());
        if (value.getFullName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFullName());
        }
        final int _tmp = value.isPrivate() ? 1 : 0;
        stmt.bindLong(3, _tmp);
        if (value.getLogin() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getLogin());
        }
        if (value.getAvatarUrl() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getAvatarUrl());
        }
        if (value.getType() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getType());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getDescription());
        }
      }
    };
  }

  @Override
  public Object insertUser(final User user, final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object getAllUsers(final Continuation<? super List<User>> $completion) {
    final String _sql = "SELECT * FROM user_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    int _argIndex = 1;
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Object _result;
      if(_cursor.moveToFirst()) {
        _result = new Object();
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
  public Object deleteAllUsers(final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
