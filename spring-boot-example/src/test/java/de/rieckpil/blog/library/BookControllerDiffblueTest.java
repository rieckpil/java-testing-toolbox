package de.rieckpil.blog.library;

import static org.mockito.Mockito.when;

import java.awt.print.Book;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BookController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BookControllerDiffblueTest {
  @Autowired
  private BookController bookController;

  @MockBean
  private BookService bookService;

  /**
   * Test {@link BookController#getBookById(Long)}.
   * <ul>
   *   <li>Given {@link BookService} {@link BookService#findById(Long)} return {@link Book} (default constructor).</li>
   *   <li>Then status {@link StatusResultMatchers#isOk()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BookController#getBookById(Long)}
   */
  @Test
  @DisplayName("Test getBookById(Long); given BookService findById(Long) return Book (default constructor); then status isOk()")
  @Tag("MaintainedByDiffblue")
  void testGetBookById_givenBookServiceFindByIdReturnBook_thenStatusIsOk() throws Exception {
    // Arrange
    when(bookService.findById(Mockito.<Long>any())).thenReturn(new Book());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/{id}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(bookController)
      .build()
      .perform(requestBuilder)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
      .andExpect(MockMvcResultMatchers.content().string("<Book><numberOfPages>0</numberOfPages></Book>"));
  }

  /**
   * Test {@link BookController#getBookById(Long)}.
   * <ul>
   *   <li>Given {@link BookService} {@link BookService#findById(Long)} return {@code null}.</li>
   *   <li>Then status {@link StatusResultMatchers#isNotFound()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BookController#getBookById(Long)}
   */
  @Test
  @DisplayName("Test getBookById(Long); given BookService findById(Long) return 'null'; then status isNotFound()")
  @Tag("MaintainedByDiffblue")
  void testGetBookById_givenBookServiceFindByIdReturnNull_thenStatusIsNotFound() throws Exception {
    // Arrange
    when(bookService.findById(Mockito.<Long>any())).thenReturn(null);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/{id}", 1L);

    // Act
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bookController).build().perform(requestBuilder);

    // Assert
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
