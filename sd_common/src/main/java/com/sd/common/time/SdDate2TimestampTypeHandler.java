package com.sd.common.time;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.executor.result.ResultMapException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedJdbcTypes({JdbcType.TIMESTAMP})  
@MappedTypes({SdDate.class})
public class SdDate2TimestampTypeHandler extends BaseTypeHandler<SdDate> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SdDate parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setTimestamp(i, convertSdDate(parameter));
        System.out.println("setNonNullParameter");
    }

    @Override
    public SdDate getNullableResult(ResultSet rs, String columnName) throws SQLException {
        
        Timestamp t =  rs.getTimestamp(columnName);
        return convertDate(t);
    }

    @Override
    public SdDate getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp t =  rs.getTimestamp(columnIndex);
        return convertDate(t);
    }

    @Override
    public SdDate getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp t =  cs.getTimestamp(columnIndex);
        return convertDate(t);
    }
    
    
      @Override
      public SdDate getResult(ResultSet rs, String columnName) throws SQLException {
          SdDate result;
        try {
          result = getNullableResult(rs, columnName);
        } catch (Exception e) {
          throw new ResultMapException("Error attempting to get column '" + columnName + "' from result set.  Cause: " + e, e);
        }
//      if (rs.wasNull()) {
//        return null;
//      } else {
          return result;
//      }
      }

      @Override
      public SdDate getResult(ResultSet rs, int columnIndex) throws SQLException {
          SdDate result;
        try {
          result = getNullableResult(rs, columnIndex);
        } catch (Exception e) {
          throw new ResultMapException("Error attempting to get column #" + columnIndex+ " from result set.  Cause: " + e, e);
        }
//      if (rs.wasNull()) {
//        return null;
//      } else {
          return result;
//      }
      }

      @Override
      public SdDate getResult(CallableStatement cs, int columnIndex) throws SQLException {
          SdDate result;
        try {
          result = getNullableResult(cs, columnIndex);
        } catch (Exception e) {
          throw new ResultMapException("Error attempting to get column #" + columnIndex+ " from callable statement.  Cause: " + e, e);
        }
//      if (cs.wasNull()) {
//        return null;
//      } else {
          return result;
//      }
      }
    
    
    
    
    private SdDate convertDate(Date t) {
        SimpleDateFormat sdf = new SimpleDateFormat(SdDate.formatPattern);

        SdDate time = new SdDate();
        if(t == null) 
        {
            time.setTime("1");
        }
        else {
            time.setTime(sdf.format(t));
        }
        
        return time ;
    }
    
    private Timestamp convertSdDate(SdDate time) {
        return Timestamp.valueOf(time.getTime());
    }
}
