import request from '@/utils/request'

// 查询人员信息列表
export function listPerson(query) {
  return request({
    url: '/family/person/list',
    method: 'get',
    params: query
  })
}

// 查询人员信息详细
export function getPerson(id) {
  return request({
    url: '/family/person/' + id,
    method: 'get'
  })
}

// 新增人员信息
export function addPerson(data) {
  return request({
    url: '/family/person',
    method: 'post',
    data: data
  })
}

// 修改人员信息
export function updatePerson(data) {
  return request({
    url: '/family/person',
    method: 'put',
    data: data
  })
}

// 删除人员信息
export function delPerson(ids) {
  return request({
    url: '/family/person/' + ids,
    method: 'delete'
  })
}

// 导出人员信息
export function exportPerson(query) {
  return request({
    url: '/family/person/export',
    method: 'post',
    params: query
  })
}
