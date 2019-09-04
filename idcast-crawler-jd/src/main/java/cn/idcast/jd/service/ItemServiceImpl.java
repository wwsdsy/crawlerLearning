package cn.idcast.jd.service;

import cn.idcast.jd.dao.ItemDao;
import cn.idcast.jd.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service    //添加service注解，由spring创建实例
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemDao itemDao;  //注入商品的dao，进行其他操作

    @Override
    public void save(Item item) {
        this.itemDao.save(item);

    }

    @Override
    public List<Item> findAll(Item item) {
        //声明查询条件
        Example<Item> example = Example.of(item);

        //根据查询条件进行查询数据
        List<Item> list = this.itemDao.findAll(example);
        return list;
    }
}
