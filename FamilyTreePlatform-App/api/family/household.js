import request from '@/utils/request'

// 查询家庭列表
export function listHousehold(query) {
  return request({
    url: '/family/household/list',
    method: 'get',
    data: query
  })
}

// 查询家庭详细
export function getHousehold(id) {
  return request({
    url: '/family/household/' + id,
    method: 'get'
  })
}

// 新增家庭
export function addHousehold(data) {
  return request({
    url: '/family/household',
    method: 'post',
    data: data
  })
}

// 修改家庭
export function updateHousehold(data) {
  return request({
    url: '/family/household',
    method: 'put',
    data: data
  })
}

// 删除家庭
export function delHousehold(ids) {
  return request({
    url: '/family/household/' + ids,
    method: 'delete'
  })
}