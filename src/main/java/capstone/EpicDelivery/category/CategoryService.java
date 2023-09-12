package capstone.EpicDelivery.category;

import capstone.EpicDelivery.exceptions.CategoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;


    public Category addCategory(Category category) throws CategoryException {
        Optional<Category> opt = categoryRepository.findById(category.getCategoryId());
        if(opt.isPresent()) {
            throw new CategoryException("Category already exists..");
        }else {
            return categoryRepository.save(category);
        }
    }



    public Category updateCategory(Category category) throws CategoryException {
        Optional<Category> opt = categoryRepository.findById(category.getCategoryId());
        if(opt.isPresent()) {
            return categoryRepository.save(category);
        }else {
            throw new CategoryException("No such Category found..");
        }
    }



    public Category viewCategory(Integer categoryId) throws CategoryException {
        Optional<Category> opt = categoryRepository.findById(categoryId);
        if(opt.isPresent()) {
            return opt.get();
        }else {
            throw new CategoryException("No Category found with ID: "+categoryId);
        }
    }


    public Category removeCategory(Integer categoryId) throws CategoryException {
        Optional<Category> opt = categoryRepository.findById(categoryId);
        if(opt.isPresent()) {
            Category cat = opt.get();
            categoryRepository.delete(cat);
            return cat;
        }else {
            throw new CategoryException("No Category found with ID: "+categoryId);
        }
    }



    public List<Category> viewAllCategory() throws CategoryException {
        List<Category> categories = categoryRepository.findAll();
        if(categories.size() > 0) {
            return categories;
        }else {
            throw new CategoryException("No Categories exists..");
        }
    }
}
