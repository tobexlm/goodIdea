package com.itheima.health.dao;

import com.itheima.health.pojo.Member;

public interface MemberDao {
    /**
     * 新增会员
     *
     * @param member
     */
    void add(Member member);
    // List<Member> findAll();
    // Page<Member> selectByCondition(String queryString);
    // void deleteById(Integer id);
    // Member findById(Integer id);

    /**
     * 通过手机号查询会员信息
     *
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);
    // void edit(Member member);
    // Integer findMemberCountBeforeDate(String date);
    // Integer findMemberCountByDate(String date);
    // Integer findMemberCountAfterDate(String date);
    // Integer findMemberTotalCount();
}
