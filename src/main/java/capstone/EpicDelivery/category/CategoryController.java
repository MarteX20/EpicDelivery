package capstone.EpicDelivery.category;

import capstone.EpicDelivery.exceptions.CategoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) throws CategoryException {
        Category newCategory = categoryService.addCategory(category);
        return new ResponseEntity<Category>(newCategory, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) throws CategoryException{
        Category updatedCategory = categoryService.updateCategory(category);
        return new ResponseEntity<Category>(updatedCategory, HttpStatus.ACCEPTED);
    }

    @GetMapping("/view/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable("categoryId") Integer categoryId) throws CategoryException{
        Category category = categoryService.viewCategory(categoryId);
        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }


    @DeleteMapping("/remove/{categoryId}")
    public ResponseEntity<Category> removeCategory(@PathVariable("categoryId") Integer categoryId) throws CategoryException{
        Category removedCategory = categoryService.removeCategory(categoryId);
        return new ResponseEntity<Category>(removedCategory, HttpStatus.OK);
    }


    @GetMapping("/viewall")
    public ResponseEntity<List<Category>> getAllCategories() throws CategoryException{
        List<Category> categories = categoryService.viewAllCategory();
        return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
    }
}
