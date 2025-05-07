package com.example.demo.domain.mapper;

import com.example.demo.domain.dto.MemoDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;



@Mapper
public interface MemoMapper {
	
	//MemoDto에서 인스턴스를 받아서 값 삽입 함수생성.
	@SelectKey(statement="select max(id)+1 from tbl_memo",keyProperty = "id",before = false, resultType = int.class)
	@Insert(value = "insert into tbl_memo values(#{id},#{text},#{writer},#{createAt})")
	public int insert(MemoDto memoDto);
	
	@Update("update tbl_memo set text=#{text} where id=#{id}")
	public int update(MemoDto dto);
	
	@Delete("delete from tbl_memo where id=#{id}")
	public int delete(int id);

	
	@Select("select * from tbl_memo where id=#{id}")
	public MemoDto selectAt(int id);
	
	
	@Select("select * from tbl_memo")
	public List<MemoDto> selectAll(); 
	
	@Results(id="MemoResultMap", value= {
			@Result(property = "id",column="id"),
			@Result(property = "text", column="text")
	})
	@Select("select * from tbl_memo")
	public List< Map<String,Object> > selectAllResultMap();
	//                id   :  a
	//                text :  abc
	
	//XML 방식
	public int insertXml(MemoDto memoDto);
	public int updateXml(MemoDto memoDto);
	public int deleteXml(@Param("id") int id);
	
	public MemoDto selectAtXml(int id);
	public List<MemoDto> selectAllXml();
	public List< Map<String,Object> > selectAllResultMapXml();
	
	public List<Map<String,Object>> Select_if_xml(Map<String,Object> param);
	public List< Map<String,Object> > Select_when_xml( Map<String,Object> param);
	
}
