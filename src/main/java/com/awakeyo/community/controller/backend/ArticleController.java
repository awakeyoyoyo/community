package com.awakeyo.community.controller.backend;

import com.awakeyo.community.cache.TagCache;
import com.awakeyo.community.exception.AuthorityException;
import com.awakeyo.community.mapper.ArticleMapper;
import com.awakeyo.community.pojo.Article;
import com.awakeyo.community.pojo.PageResult;
import com.awakeyo.community.pojo.User;
import com.awakeyo.community.pojo.dto.ArticleDto;
import com.awakeyo.community.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author awakeyoyoyo
 * @className ArticleController
 * @description TODO
 * @date 2020-02-27 19:40
 */
@Controller
public class ArticleController {
    @Value("${neverDie.user}")
    private String rootUser;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleMapper articleMapper;
    //写博客
    @PostMapping("/writeBlog")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "content",required = false)String context,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id") Integer id,
            HttpServletRequest request,
            Model model
    ){
        /*
        * 管理员身份
        * */
        User user=(User)request.getSession().getAttribute("user");
        if (user==null){
            throw new AuthorityException("未登陆就调用管理员接口，你小子？？？");
        }
        //校验一下是否是管理员
        if (!user.getAccountId().equals(rootUser)){
            throw new AuthorityException("你小子？？？居然找到了管理员接口？？？有点东西");
        }
        model.addAttribute("title",title);
        model.addAttribute("content",context);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.getInstance().get());
        if (user==null){
            model.addAttribute("error","用户未登陆！！！！");
            return "publicAritle";
        }
        if (title==null||title==""){
            model.addAttribute("error","标题不能为空！！！！");
            return "publicAritle";
        }
        if (description==null||description==""){
            model.addAttribute("error","问题描述不能为空！！！！");
            return "publicAritle";
        }
        if (tag==null||tag==""){
            model.addAttribute("error","标签不能为空！！！！");

            return "publicAritle";
        }
        if (context==null||context==""){
            model.addAttribute("error","文章内容不能为空！！！！");
            return "publicAritle";
        }
        String invalid=TagCache.getInstance().filterInvalid(tag);
        if (!StringUtils.isEmpty(invalid)){
            model.addAttribute("error","输入非法标签"+invalid);
            return "publicAritle";
        }
        Article article=new Article();
        article.setTitle(title);
        article.setDecription(description);
        article.setTag(tag);
        article.setContent(context);
        article.setCreator(user.getId());
        article.setLikeCount(0);
        article.setViewCount(0);
        article.setCommentCount(0);
        article.setId(id);
        articleService.insertOrUpdate(article);
        model.addAttribute("tags", TagCache.getInstance().get());
        return "redirect:/";
    }
    //删除博客
    //改博客
    //查
    @GetMapping("/Aritle/{id}")
    public String aritle(HttpServletRequest request, @PathVariable(name = "id") Integer id,
                         Model model){
        ArticleDto articleDto=articleService.geiArtleById(id);
        model.addAttribute("articleDto",articleDto);
        return "article";
    }


    @GetMapping("/editBlog")
    public String editBlog(Integer id,Model model,HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        if (user==null){
            throw new AuthorityException("未登陆就调用管理员接口，你小子？？？");
        }
        //校验一下是否是管理员
        if (!user.getAccountId().equals(rootUser)){
            throw new AuthorityException("你小子？？？居然找到了管理员接口？？？有点东西");
        }
        Article question=articleMapper.selectByPrimaryKey(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDecription());
        model.addAttribute("content",question.getContent());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.getInstance().get());
        return "publicArticle";
    }

    @GetMapping("/publishBlog")
    public String publishBlog(Model model,HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        if (user==null){
            throw new AuthorityException("未登陆就调用管理员接口，你小子？？？");
        }
        //校验一下是否是管理员
        if (!user.getAccountId().equals(rootUser)){
            throw new AuthorityException("写博客功能只对站主开放。。你小子？？？居然找到了我写博客的接口？？？有点东西");
        }
        model.addAttribute("tags", TagCache.getInstance().get());
        return "publicArticle";
    }
    @GetMapping("/articles")
    public String getArticle(@RequestParam(name = "pageNo", defaultValue = "1", required = false) Integer pageNo,
                             @RequestParam(name = "pageSize", defaultValue = "3", required = false) Integer pageSize,
                                          HttpServletRequest request,
                                          Model model){

        PageResult<ArticleDto> pageResult;
        pageResult = articleService.getList(pageNo, pageSize);
        model.addAttribute("pageResult", pageResult);
        return "index::AticleBlog";
    }
}
