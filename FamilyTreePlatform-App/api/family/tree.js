import request from '@/utils/request'

// 查询族谱列表
export function listTree(query) {
  return request({
    url: '/family/tree/list',
    method: 'get',
    data: query
  })
}

// 查询族谱详细
export function getTree(id) {
  return request({
    url: '/family/tree/' + id,
    method: 'get'
  })
}

// 新增族谱
export function addTree(data) {
  return request({
    url: '/family/tree',
    method: 'post',
    data: data
  })
}

// 修改族谱
export function updateTree(data) {
  return request({
    url: '/family/tree',
    method: 'put',
    data: data
  })
}

// 删除族谱
export function delTree(ids) {
  return request({
    url: '/family/tree/' + ids,
    method: 'delete'
  })
}

// 获取族谱可视化数据
export function getTreeVisualization(id) {
  return request({
    url: '/family/tree/visualize/' + id,
    method: 'get'
  })
}